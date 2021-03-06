package org.wit.hillfortapp.views.map

import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import kotlinx.android.synthetic.main.drawer_main.*
import kotlinx.android.synthetic.main.recycle_item_hillfort.view.*
import org.wit.hillfortapp.R
import org.wit.hillfortapp.helpers.readImageFromPath
import org.wit.hillfortapp.models.HillfortModel
import org.wit.hillfortapp.views.BaseView


class HillfortMapsView : BaseView(), GoogleMap.OnMarkerClickListener {

    private lateinit var presenter: HillfortMapsPresenter
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content_frame.removeAllViews()
        layoutInflater.inflate(R.layout.activity_hillfort_maps, content_frame)

        presenter = initPresenter(HillfortMapsPresenter(this)) as HillfortMapsPresenter

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadHillforts()
        }

        bottomNavBar.menu.findItem(R.id.navigation_map).isChecked = true
    }

    override fun showHillfort(hillfort: HillfortModel) {
        currentTitle.text = hillfort.name
        currentRating.rating = hillfort.rating.toFloat()
        if(hillfort.images.isNotEmpty()) {
            Glide.with(currentImage.context).load(hillfort.images[0].uri).centerCrop()
                .into(currentImage)
        }
        else {
            Glide.with(currentImage.context).load(R.drawable.placeholder).centerCrop()
                .into(currentImage)
        }
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {
        presenter.doPopulateMap(map, hillforts)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}