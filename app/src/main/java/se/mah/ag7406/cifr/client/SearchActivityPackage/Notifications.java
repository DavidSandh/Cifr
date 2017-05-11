package se.mah.ag7406.cifr.client.SearchActivityPackage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import se.mah.ag7406.cifr.R;
import se.mah.ag7406.cifr.client.ContactListPackage.ContactList;

/**
 * Created by 123 on 2017-05-11.
 */

public class Notifications extends AppCompatActivity {







// https://developer.android.com/training/notify-user/build-notification.html#notify
    //https://developer.android.com/guide/topics/ui/notifiers/notifications.html


    public void sendNotification(String user ){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_share)
                .setContentTitle("En användre har lagt till dig.")
                .setContentText( user +" vill lägga till dig i sin kontaktlista.");

        Intent intent = new Intent(this, ContactList.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setContentIntent(resultPendingIntent);

        int mNotificationId = 000;

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }


}

