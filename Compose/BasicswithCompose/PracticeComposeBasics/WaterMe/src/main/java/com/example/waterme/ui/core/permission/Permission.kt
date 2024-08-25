package com.example.waterme.ui.core.permission

interface Permission {
    /**
     * Sends a request.
     *
     * @return `true` if the request was successful, `false` otherwise.
     */
    fun request(): Boolean

    /**
     * Checks if the rationale dialog should be shown.
     *
     * This method determines whether the rationale dialog explaining the need for a certain permission
     * should be displayed to the user. It typically checks if the permission has been requested before
     * and the user denied it without checking the "Don't ask again" option.
     *
     * @return `true` if the rationale dialog should be shown, `false` otherwise.
     */
    fun shouldShowRationale(): Boolean

    /**
     * Checks if the permission is granted.
     *
     * @return `true` if the permission is granted, `false` otherwise.
     */
    fun isGranted(): Boolean
}
