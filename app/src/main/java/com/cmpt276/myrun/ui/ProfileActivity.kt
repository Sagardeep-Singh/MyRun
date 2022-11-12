package com.cmpt276.myrun.ui

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.preference.PreferenceManager
import com.cmpt276.myrun.BuildConfig
import com.cmpt276.myrun.R
import com.cmpt276.myrun.Util
import com.cmpt276.myrun.databinding.ActivityProfileBinding
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*

class ProfileActivity : AppCompatActivity() {

    object ProfilePictureConstants {
        const val KEY = "profile_picture.jpg"
        const val TEMP_KEY = "temp_picture.jpg"
    }

    private lateinit var cameraResult: ActivityResultLauncher<Intent>

    private lateinit var profilePictureUri: Uri
    private lateinit var tempPictureUri: Uri

    private lateinit var binding: ActivityProfileBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)

        Util.checkPermissions(this)

        setupCameraActivityResultCallback()
        setupTempProfilePictureFile()
        loadUserProfile()

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    private fun setupCameraActivityResultCallback() {
        cameraResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val bitmap = Util.getBitmap(this, tempPictureUri)
                    binding.contentMain.ivProfilePhoto.setImageBitmap(bitmap)
                }
            }
    }

    private fun setupTempProfilePictureFile() {
        val tempImgFile = File(
            getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            ),
            ProfilePictureConstants.TEMP_KEY
        )

        tempPictureUri = FileProvider.getUriForFile(
            Objects.requireNonNull(applicationContext),
            BuildConfig.APPLICATION_ID, tempImgFile
        )
    }

    private fun loadUserProfile() {
        val name = preferences.getString("name", "")
        val email = preferences.getString("email", "")
        val phone = preferences.getString("phone", "")
        val userClass = preferences.getString("class", "")
        val major = preferences.getString("major", "")
        val gender = preferences.getInt("gender", 0)

        binding.contentMain.etName.setText(name)
        binding.contentMain.etEmail.setText(email)
        binding.contentMain.etPhone.setText(phone)
        binding.contentMain.etClass.setText(userClass)
        binding.contentMain.etMajor.setText(major)
        binding.contentMain.rgGender.check(gender)

        val profilePictureFile =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ProfilePictureConstants.KEY)

        profilePictureUri = FileProvider.getUriForFile(
            Objects.requireNonNull(applicationContext),
            BuildConfig.APPLICATION_ID, profilePictureFile
        )

        profilePictureFile.let {
            if (it.exists()) {
                binding.contentMain.ivProfilePhoto.setImageURI(profilePictureUri)
            }
        }

    }

    private fun saveUserProfile() {
        val editor = preferences.edit()
        editor.putString("name", binding.contentMain.etName.text.toString())
        editor.putString("email", binding.contentMain.etEmail.text.toString())
        editor.putString("phone", binding.contentMain.etPhone.text.toString())
        editor.putString("class", binding.contentMain.etClass.text.toString())
        editor.putString("major", binding.contentMain.etMajor.text.toString())
        editor.putInt("gender", binding.contentMain.rgGender.checkedRadioButtonId)
        editor.apply()

        tempPictureUri.path?.let { tempPathStr ->
            val tempFile = File(tempPathStr)

            if (tempFile.exists()) {
                Files.move(
                    tempFile.toPath(),
                    profilePictureUri.path?.let { File(it).toPath() },
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onChangeClicked(view: View) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempPictureUri)
        cameraResult.launch(intent)
    }

    fun onSaveClicked(view: View) {
        saveUserProfile()
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    fun onCancelClicked(view: View) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        this.finish()
    }
}