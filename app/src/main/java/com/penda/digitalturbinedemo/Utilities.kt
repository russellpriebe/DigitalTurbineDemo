package com.penda.digitalturbinedemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

object Utilities {

        val GET = "GET"

        fun getVersion(): String {
            val cal = Calendar.getInstance()
            cal.time = Date()
            val dayString = cal.get(Calendar.DAY_OF_MONTH).toString()
            val month = (cal.get(Calendar.MONTH)+1)
            var monthString=month.toString()
            if(month<10) monthString = "0$month"
            val yearString = cal.get(Calendar.YEAR).toString()
            return "$yearString$monthString$dayString"
        }

        fun httpGET(url: String): Bundle? {
            var httpConnection: HttpURLConnection? = null
            var inputReader: InputStreamReader? = null
            var bufferedReader: BufferedReader? = null
            lateinit var readBuffer: StringBuffer
            var bundle: Bundle? = Bundle()

            try{
                val url = URL(url)
                httpConnection = getHttpConnection(url)
                inputReader = InputStreamReader(httpConnection.inputStream)
                bufferedReader = BufferedReader(inputReader)
                readBuffer = StringBuffer()
                var line = bufferedReader.readLine()
                while(line!=null){
                    readBuffer.append(line)
                    line = bufferedReader.readLine()
                }
                bundle?.putString("valid", readBuffer.toString())
            } catch (m: MalformedURLException) {
                bundle?.putString("malformedURL", m.toString())
            } catch (io: IOException){
                bundle?.putString("io", io.toString())
            } finally {
                bufferedReader?.let{
                    it.close()
                    bufferedReader = null
                }
                inputReader?.let{
                    it.close()
                    inputReader = null
                }
                httpConnection?.let{
                    it.disconnect()
                    httpConnection = null
                }
            }
            return bundle
        }

        fun getJPG(url: String) : Bitmap? {
            var httpConnection: HttpURLConnection? = null
            var inputReader: InputStreamReader? = null
            var bm: Bitmap? = null
            var statusCode: Int? = 0

            try{
                val url = URL(url)
                httpConnection = getHttpConnection(url)
                statusCode = httpConnection.responseCode
                bm = BitmapFactory.decodeStream(httpConnection.inputStream)
            } catch (m: MalformedURLException) {
            } catch (io: IOException){
            } finally {
                inputReader?.let{
                    it.close()
                    inputReader = null
                }
                httpConnection?.let{
                    it.disconnect()
                    httpConnection = null
                }
            }
            return bm
        }

        private fun getHttpConnection(url: URL) : HttpURLConnection {
            val httpConnection = url.openConnection() as HttpURLConnection
            httpConnection.apply{
                requestMethod = GET
                connectTimeout = 10000
                readTimeout = 10000
            }
            return httpConnection
        }
}