/*
* Copyright 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.notificationchannels

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Display main screen for sample. Displays controls for sending test notifications.
 */
class MainActivity : Activity() {

    private lateinit var ui: MainUi

    /*
     * Class for managing notifications
     */
    private lateinit var helper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ui = MainUi(activity_main)
        helper = NotificationHelper(this)
    }

    /**
     * Send activity notifications.

     * @param id The ID of the notification to create
     * *
     * @param title The title of the notification
     */
    fun sendNotification(id: Int, title: String) {
        when (id) {
            NOTI_PRIMARY1 -> helper.notify(
                    id, helper.getNotification1(title, getString(R.string.primary1_body)))
            NOTI_PRIMARY2 -> helper.notify(
                    id, helper.getNotification1(title, getString(R.string.primary2_body)))
            NOTI_SECONDARY1 -> helper.notify(
                    id, helper.getNotification2(title, getString(R.string.secondary1_body)))
            NOTI_SECONDARY2 -> helper.notify(
                    id, helper.getNotification2(title, getString(R.string.secondary2_body)))
        }
    }

    /**
     * Send Intent to load system Notification Settings for this app.
     */
    fun goToNotificationSettings() {
        val i = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(i)
    }

    /**
     * Send intent to load system Notification Settings UI for a particular channel.

     * @param channel Name of channel to configure
     */
    fun goToNotificationSettings(channel: String) {
        val i = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel)
        startActivity(i)
    }

    /**
     * View model for interacting with Activity UI elements. (Keeps core logic for sample
     * seperate.)
     */
    internal inner class MainUi (root: View) : View.OnClickListener {

        init {
            main_primary_send1.setOnClickListener(this)
            main_primary_send2.setOnClickListener(this)
            main_primary_config.setOnClickListener(this)

            main_secondary_send1.setOnClickListener(this)
            main_secondary_send2.setOnClickListener(this)
            main_secondary_config.setOnClickListener(this)

            (root.findViewById<View>(R.id.btnA) as Button).setOnClickListener(this)
        }

        private val titlePrimaryText: String
            get() {
                if (main_primary_title != null) {
                    return main_primary_title.text.toString()
                }
                return ""
            }

        private val titleSecondaryText: String
            get() {
                if (main_primary_title != null) {
                    return main_secondary_title.text.toString()
                }
                return ""
            }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.main_primary_send1 -> sendNotification(NOTI_PRIMARY1, titlePrimaryText)
                R.id.main_primary_send2 -> sendNotification(NOTI_PRIMARY2, titlePrimaryText)
                R.id.main_primary_config -> goToNotificationSettings(NotificationHelper.PRIMARY_CHANNEL)

                R.id.main_secondary_send1 -> sendNotification(NOTI_SECONDARY1, titleSecondaryText)
                R.id.main_secondary_send2 -> sendNotification(NOTI_SECONDARY2, titleSecondaryText)
                R.id.main_secondary_config -> goToNotificationSettings(NotificationHelper.SECONDARY_CHANNEL)
                R.id.btnA -> goToNotificationSettings()
                else -> Log.e(TAG, "Unknown click event.")
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        private val NOTI_PRIMARY1 = 1100
        private val NOTI_PRIMARY2 = 1101
        private val NOTI_SECONDARY1 = 1200
        private val NOTI_SECONDARY2 = 1201
    }
}
