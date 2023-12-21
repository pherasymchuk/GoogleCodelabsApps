package com.example.android.marsphotos.overview

interface MarsApiStatus {
    object Loading : MarsApiStatus
    object Error : MarsApiStatus
    object Done : MarsApiStatus
}
