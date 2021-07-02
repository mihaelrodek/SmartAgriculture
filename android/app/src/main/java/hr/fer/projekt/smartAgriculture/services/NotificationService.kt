package hr.fer.projekt.smartAgriculture.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hr.fer.projekt.smartAgriculture.R
import hr.fer.projekt.smartAgriculture.activities.AddNewTaskActivity
import hr.fer.projekt.smartAgriculture.model.NotificationModel
import hr.fer.projekt.smartAgriculture.model.User
import hr.fer.projekt.smartAgriculture.repository.Repository
import hr.fer.projekt.smartAgriculture.util.Constants.Companion.CHANNEL_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class NotificationService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, a: Int, startId: Int): Int {
        sendNotifications()
        return START_REDELIVER_INTENT
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        if (KEEP_RUNNING)
            sendNotifications()
        else
            stopSelf()
    }

    private fun sendNotifications() {
        val repository = Repository()
        CoroutineScope(Dispatchers.IO).launch {
            while (KEEP_RUNNING) {
                var notifications: Response<List<NotificationModel>> =
                    repository.getAllNotifications("Bearer " + User.user.token)
                if (!notifications.isSuccessful)
                    continue

                for (n in notifications.body()!!) {
                    val NotificationIntent =
                        Intent(this@NotificationService, AddNewTaskActivity::class.java)
                            .putExtra("message", n.message)
                            .apply {
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }

                    val pendingIntent: PendingIntent =
                        PendingIntent.getActivity(
                            this@NotificationService,
                            0,
                            NotificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )

                    val builder = buildNotification(n.message, pendingIntent)

                    with(NotificationManagerCompat.from(this@NotificationService)) {
                        if (builder != null) {
                            notify(n.time.time.toInt(), builder.build())
                        }
                    }
                }

                delay(50000)

            }
        }
    }

    private fun buildNotification(
        notificationText: String,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder? {
        return NotificationCompat.Builder(this@NotificationService, CHANNEL_ID)
            .setSmallIcon(R.drawable.kapljice)
            .setContentTitle(resources.getString(R.string.warning))
            .setContentText(notificationText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(notificationText)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    companion object {
        var KEEP_RUNNING = true
    }
}
