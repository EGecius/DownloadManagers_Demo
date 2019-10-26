package com.spartons.downloadmanager

import com.egecius.downloads.ActivityWrapper
import com.egecius.downloads.DirectoryHelper
import com.egecius.downloads.PermissionsDelegate
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PermissionsDelegateTest {

    private val requestCode: Int = 237
    private var grantResults: IntArray = IntArray(1)

    private lateinit var sut: PermissionsDelegate

    @Mock
    private lateinit var activityWrapper: ActivityWrapper
    @Mock
    private lateinit var directoryHelper: DirectoryHelper
    @Mock
    private lateinit var listener: PermissionsDelegate.Listener

    @Before
    fun setUp() {
        sut = PermissionsDelegate(directoryHelper, activityWrapper)
    }

    @Test
    fun `requests permission storage for Android M`() {
        givenIsAndroidVersionAtLeastM()

        sut.requestPermission(listener)

        verify(activityWrapper).requestPermissionsExternalStorage()
    }

    private fun givenIsAndroidVersionAtLeastM() {
        given(activityWrapper.isBuildVersionCodeM()).willReturn(true)
    }

    @Test
    fun `creates directory immediately for below Android M`() {
        givenAndroidVersionBelowM()

        sut.requestPermission(listener)

        verify(directoryHelper).createDirectoryIfMissing()
    }

    @Test
    fun `notifies listener immediately for below Android M`() {
        givenAndroidVersionBelowM()

        sut.requestPermission(listener)

        verify(listener).onPermissionGranted()
    }

    private fun givenAndroidVersionBelowM() {
        given(activityWrapper.isBuildVersionCodeM()).willReturn(false)
    }

    /* on Permission granted */

    @Test
    fun `creates directory when permission granted`() {
        givenIsAndroidVersionAtLeastM()
        sut.requestPermission(listener)
        givenPermissionWillBeGranted()

        sut.onRequestPermissionsResult(requestCode, grantResults)

        verify(directoryHelper).createDirectoryIfMissing()
    }

    private fun givenPermissionWillBeGranted() {
        given(activityWrapper.isPermissionGrantedExternalStorage(requestCode, grantResults)).willReturn(true)
    }

    @Test
    fun `notifies listener when permission granted`() {
        givenIsAndroidVersionAtLeastM()
        sut.requestPermission(listener)
        givenPermissionWillBeGranted()

        sut.onRequestPermissionsResult(requestCode, grantResults)

        verify(listener).onPermissionGranted()
    }
}