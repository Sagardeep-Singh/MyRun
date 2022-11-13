package com.cmpt276.myrun.ui

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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
    private lateinit var galleryResult: ActivityResultLauncher<Intent>

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
        setupGalleryActivityResultCallback()
        setupTempProfilePictureFile()
        loadUserProfile()


        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.action_settings)

        setContentView(binding.root)
    }

    private fun setupCameraActivityResultCallback() {
        cameraResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val bitmap = Util.getBitmap(this, tempPictureUri)
                    binding.ivProfilePhoto.setImageBitmap(bitmap)
                }
            }
    }

    private fun setupGalleryActivityResultCallback() {
        galleryResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val bitmap = Util.getBitmap(this, result.data?.data!!)
                    Util.saveBitmapToFile( bitmap, tempPictureUri)
                    binding.ivProfilePhoto.setImageBitmap(bitmap)
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

        binding.etName.setText(name)
        binding.etEmail.setText(email)
        binding.etPhone.setText(phone)
        binding.etClass.setText(userClass)
        binding.etMajor.setText(major)
        binding.rgGender.check(gender)

        val profilePictureFile =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ProfilePictureConstants.KEY)

        profilePictureUri = FileProvider.getUriForFile(
            Objects.requireNonNull(applicationContext),
            BuildConfig.APPLICATION_ID, profilePictureFile
        )

        profilePictureFile.let {
            if (it.exists()) {
                binding.ivProfilePhoto.setImageURI(profilePictureUri)
            }
        }

    }

    private fun saveUserProfile() {
        val editor = preferences.edit()
        editor.putString("name", binding.etName.text.toString())
        editor.putString("email", binding.etEmail.text.toString())
        editor.putString("phone", binding.etPhone.text.toString())
        editor.putString("class", binding.etClass.text.toString())
        editor.putString("major", binding.etMajor.text.toString())
        editor.putInt("gender", binding.rgGender.checkedRadioButtonId)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onChangeClicked(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Change Profile Picture")
            .setMessage("Choose an option")
            .setPositiveButton("Camera") { _, _ ->
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempPictureUri)
                cameraResult.launch(intent)
            }
            .setNegativeButton("Gallery") { _, _ ->
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                galleryResult.launch(intent)
            }
            .setNeutralButton("Cancel") { _, _ -> }
            .show()
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