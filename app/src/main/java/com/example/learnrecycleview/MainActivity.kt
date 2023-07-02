package com.example.learnrecycleview

import android.os.Bundle
import android.widget.Button
//import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.learnrecycleview.ui.theme.LearnRecycleviewTheme
//import kotlinx.coroutines.DefaultExecutor.thread
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		// getting the recyclerview by its id
		val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

		// this creates a vertical layout Manager
		recyclerview.layoutManager = LinearLayoutManager(this)

		val button = findViewById<Button>(R.id.btn_refresh)
		button.setOnClickListener {
				getHtmlFromWeb()
		}


		// ArrayList of class ItemsViewModel
//		val data = ArrayList<ItemsViewModel>()


		// This loop will create 20 Views containing
		// the image with the count of view
//		for (i in 1..20) {
//			data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, "Item " + i))
//		}




	}

	private fun getHtmlFromWeb() {
		val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
//		val data = null
		GlobalScope.launch (Dispatchers.Main){
			var data = withContext(Dispatchers.IO){
				GetCnbetaData()
			}
			if (data != null) {
				// This will pass the ArrayList to our Adapter
				val adapter = CustomAdapter(data)

				// Setting the Adapter with the recyclerview
				recyclerview.adapter = adapter
			}
		}

	}

	private fun GetCnbetaData(): List<ItemsViewModel> {
		val data = ArrayList<ItemsViewModel>()

		val url = "https://m.cnbeta.com.tw/"

		val doc = Jsoup.connect(url).get()
//		val html = doc.outerHtml()
		val txtThumb = doc.getElementsByClass("txt_thumb")

		var i=0
		for (tt in txtThumb)
		{
			val img = tt.getElementsByTag("img")
//			println(img.attr("src"))
//			println(img.attr("alt"))
			data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, i.toString() +". "+ img.attr("alt")))
			i++
		}

		return data
	}
}





@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	LearnRecycleviewTheme {
		Greeting("Android")
	}
}