package com.dimetris.uploadmultiimage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import java.io.File
import java.net.URI.create


class MainActivity : AppCompatActivity() {

    val tkPhotos = ArrayList<TkPhoto>()
    var theMax = 10
    var theLimit = tkPhotos.size

    lateinit var photosRecView: PhotosRecView

    val client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photosRecView = PhotosRecView(tkPhotos, this)
        PhotosRecycler.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        PhotosRecycler.adapter = photosRecView

        ClickToGet.setOnClickListener {
            theMax = tkPhotos.size
            theLimit = 10 - theMax
            ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Album")
                .setDirectoryName("Image Picker")
                .setMultipleMode(true)
                .setShowNumberIndicator(true)
                .setMaxSize(theLimit)
                .setLimitMessage("You can select up to 10 images")
                .setShowCamera(true)
                .setRequestCode(100)
                .start()
        }


        InTheNameOfAllah.setOnClickListener {
//            postMultipart()
            try {
                val multipartBody = MultipartBody.Builder()
                multipartBody.setType(MultipartBody.FORM)
                multipartBody.addFormDataPart("unit_name", "newnewnenwenwe")
                multipartBody.addFormDataPart("nickname", "newnewnenwenwe")
                multipartBody.addFormDataPart("unit_type", "2")
                multipartBody.addFormDataPart("suitable", "1")
                multipartBody.addFormDataPart("privacy", "FML")
                multipartBody.addFormDataPart("description", "testest")

                multipartBody.addFormDataPart("amenities[]", "1")
                multipartBody.addFormDataPart("amenities[]", "2")
                multipartBody.addFormDataPart("amenities[]", "3")
                multipartBody.addFormDataPart("amenities[]", "4")

                multipartBody.addFormDataPart("facilities[]", "1,100")
                multipartBody.addFormDataPart("facilities[]", "2,200")
                multipartBody.addFormDataPart("facilities[]", "3,300")
                Log.e("Progress1", "Progress")
                for (d in tkPhotos.indices) {
                    val file1 = File(tkPhotos[d].PhotoURI)
                    val fileRequestBody = file1.asRequestBody("image/png".toMediaType())
                    val imagename = System.currentTimeMillis().toString()
                    multipartBody.addFormDataPart("images[]", imagename, fileRequestBody)
                }
                Log.e("Progress2", "Progress")
                val requestBody: RequestBody = multipartBody.build()

                val request:Request= Request.Builder()
                    .addHeader("Authorization", "Token 9d99f7cec84ce83aec2c8ff8db06ddc87d9c3d7a")
                    .addHeader("Project-Token", "11a6c121-c7d6-4325-bb77-fb364bbed9fd")
                    .url("https://testing.taakeedsa.com/api/v1/unit/create_unit")
                    .post(requestBody)
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    @Throws(IOException::class)
                    override fun onFailure(call: Call, e: java.io.IOException) {
                        runOnUiThread { Log.e("onFailure","onFailure")}
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread { Log.e("onResponse",response.message.toString()+"A7a")
                            Log.e("onResponse",response.body!!.string().toString())
                            Log.e("onResponse",response.toString())}
                    }
                })
            } catch (e: IOException) {
                Log.e("TryCatchFinal",e.message.toString()+"A7a")
                e.printStackTrace()
            }
        }






    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 100)) {
            // Get a list of picked images
            val selectedphotos: List<TkPhoto> = GetNewPhotos(ImagePicker.getImages(data))!!
            for (d in selectedphotos.indices) {
                tkPhotos.add(selectedphotos[d])
            }
            theMax = tkPhotos.size
            theLimit = 10 - theMax
            photosRecView.notifyDataSetChanged()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun postMultipart(){

        val thread = Thread {
            try {
                Log.e("Progress", "Progress")

                val multipartBody = MultipartBody.Builder()
                multipartBody.addFormDataPart("unit_name", "newnewnenwenwe")
                multipartBody.addFormDataPart("nickname", "newnewnenwenwe")
                multipartBody.addFormDataPart("unit_type", "2")
                multipartBody.addFormDataPart("suitable", "1")
                multipartBody.addFormDataPart("privacy", "FML")
                multipartBody.addFormDataPart("description", "testest")
                Log.e("Progress1", "Progress")

                for (d in tkPhotos.indices) {
                    val file1 = File(tkPhotos[d].PhotoURI)
                    val fileRequestBody = file1.asRequestBody("image/png".toMediaType())
                    val imagename = System.currentTimeMillis().toString()
                    multipartBody.addFormDataPart("images[]", imagename, fileRequestBody)
                }
                Log.e("Progress2", "Progress")
                val requestBody: RequestBody = multipartBody.build()

                val request:Request=
                    Request.Builder().addHeader("Authorization", "Token 9d99f7cec84ce83aec2c8ff8db06ddc87d9c3d7a")
                        .addHeader("Project-Token", "11a6c121-c7d6-4325-bb77-fb364bbed9fd")
                        .url("https://testing.taakeedsa.com/api/v1/unit/create_unit")
                        .post(requestBody)
                        .build()
                Log.e("Progress3", "Progress")


                val client = OkHttpClient()
                val response = client.newCall(request).execute()
                Log.e("Progress4", "Progress")
                Log.e("Response", response.message)
//                return response.message.toString()
            } catch (e: Exception) {
                Log.e("Progress5", "Progress")
                Log.e("ErrorResponse", e.message.toString())
                e.printStackTrace()
            }
            Log.e("Progress6", "Progress")

        }
        thread.start()


    }

    fun GetNewPhotos(imageArrayList: List<Image>): List<TkPhoto> {
        val NewPhotosArr = ArrayList<TkPhoto>()
        for (d in imageArrayList.indices) {
            NewPhotosArr.add(
                TkPhoto(
                    imageArrayList[d].id,
                    imageArrayList[d].name,
                    imageArrayList[d].path
                )
            )
        }
        return NewPhotosArr
    }





}