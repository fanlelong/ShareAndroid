package com.ancely.netan.request.okhttp;

public interface ProgressListener {

    void onProgress(int progress, String url);

}