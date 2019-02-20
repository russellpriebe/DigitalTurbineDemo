package com.penda.digitalturbinedemo


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class MainViewModel: ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    var returnBundle: MutableLiveData<ArrayList<Ad>> = MutableLiveData()
    var errorBundle:  MutableLiveData<String> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun fetchCampaigns(url: String){
        uiScope.launch(Dispatchers.IO){
                Utilities.httpGET(url)?.let{
                when {
                    it.containsKey("valid") -> { val data: String? = it.getString("valid")
                        data?.let{
                            returnBundle.postValue(parseXML(data))
                        }
                    }
                    it.containsKey("malformedURL") -> { val data: String? = it.getString("malformedURL")
                        data?.let {
                            errorBundle.postValue(data)
                        }
                    }
                    it.containsKey("io") -> {
                        val data: String? = it.getString("io")
                        data?.let {
                            errorBundle.postValue(data)
                        }
                    }
                    else -> { errorBundle.postValue("Unknown Error")}
                }
            }?:run{
                    errorBundle.postValue("Unknown Error")
            }

        }
    }

    private fun parseXML(xml: String): ArrayList<Ad>? {
        var mAdList: ArrayList<Ad>? = ArrayList<Ad>()
        val factory: DocumentBuilderFactory
        val builder: DocumentBuilder
        var dom: Document

        try {
            factory = DocumentBuilderFactory.newInstance()
            builder = factory.newDocumentBuilder()
            val sr = StringReader(xml)
            var `is` = InputSource(sr)
            dom = builder.parse(`is`)
            val mList = dom.getElementsByTagName("ad")
            val len = mList.length
            for(i in 0 until len){
                try {
                    val node = mList.item(i)
                    if (node.nodeType == Node.ELEMENT_NODE) {
                        val element = node as Element
                        val appId = getValue("appId", element)
                        val averageRatingImageURL = getValue("averageRatingImageURL", element)
                        val bidRate = getValue("bidRate", element)
                        val billingTypeId = getValue("billingTypeId", element)
                        val callToAction = getValue("callToAction", element)
                        val campaignDisplayOrder = getValue("campaignDisplayOrder", element)
                        val campaignId = getValue("campaignId", element)
                        val campaignTypeId = getValue("campaignTypeId", element)
                        val categoryName = getValue("categoryName", element)
                        val clickProxyURL = getValue("clickProxyURL", element)
                        val creativeId = getValue("creativeId", element)
                        val homeScreen = getValue("homeScreen", element)
                        val impressionTrackingURL = getValue("impressionTrackingURL", element)
                        val isRandomPick = getValue("isRandomPick", element)
                        val numberOfRatings = getValue("numberOfRatings", element)
                        val productDescription = getValue("productDescription", element)
                        val productId = getValue("productId", element)
                        val productName = getValue("productName", element)
                        val productThumbnail = getValue("productThumbnail", element)
                        val rating = getValue("rating", element)
                        mAdList?.add(
                            Ad(
                                appId,
                                averageRatingImageURL,
                                bidRate,
                                billingTypeId,
                                callToAction,
                                campaignDisplayOrder,
                                campaignId,
                                campaignTypeId,
                                categoryName,
                                clickProxyURL,
                                creativeId,
                                homeScreen,
                                impressionTrackingURL,
                                isRandomPick,
                                numberOfRatings,
                                productDescription,
                                productId,
                                productName,
                                productThumbnail,
                                rating
                            )
                        )
                    }
                } catch (e: Exception){

                }

            }

        } catch (e: Exception) {
            return null
        }
        return mAdList
    }

    fun getValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag).item(0).childNodes
        val node = nodeList.item(0)
        return node.nodeValue
    }
}