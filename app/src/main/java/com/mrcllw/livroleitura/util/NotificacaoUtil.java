package com.mrcllw.livroleitura.util;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.mrcllw.livroleitura.R;
import com.mrcllw.livroleitura.model.Livro;

import java.util.Calendar;

/**
 * Created by Marcello on 20/11/2017.
 */

public class NotificacaoUtil extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Livro livro = (Livro) intent.getSerializableExtra("livro");

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Um livro esta a sua espera !")
                .setContentText("Não se esqueça da leitura do livro " + livro.getNome() + " !")
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(alarmSound);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationCompat.build());
    }

    public static void criarNotificacao(Context context, Calendar dateTime, Livro livro){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificacaoUtil.class);
        intent.putExtra("livro", livro);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, livro.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, dateTime.getTimeInMillis(), pendingIntent);
    }
}
