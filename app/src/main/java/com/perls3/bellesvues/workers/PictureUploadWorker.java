package com.perls3.bellesvues.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.net.URL;

/**
 *  this is a worker so send pictures to fire storage after it has been token, this worker have network constarint
 *  and pass as output the the url of the picture
 */

public class PictureUploadWorker extends Worker {
    private String imagePath;

    public PictureUploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }


    public URL uploadImage(String path){



        return null;
    }




}
