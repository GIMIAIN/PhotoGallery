package com.bignerdranch.android.photogallery

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

//private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoGalleryViewModel = ViewModelProvider(this)[PhotoGalleryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        photoRecyclerView = view.findViewById(R.id.photoRecyclerView)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner
        ) { galleryItems ->
            photoRecyclerView.adapter = PhotoAdapter(galleryItems)
        }
    }

    private class PhotoHolder(itemImageView: ImageView)
        : RecyclerView.ViewHolder(itemImageView) {

        val bindDrawable: (Drawable) -> Unit = itemImageView::setImageDrawable
    }

    private inner class PhotoAdapter(private val galleryItems: List<GalleryItem>)
        : RecyclerView.Adapter<PhotoHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PhotoHolder {
            val view = layoutInflater.inflate(
                R.layout.list_item_gallery,
                parent,
                false
            ) as ImageView
            return PhotoHolder(view)
        }

        override fun getItemCount(): Int = galleryItems.size

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            //val galleryItem = galleryItems[position]
            val placeholder: Drawable = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.no_image_placeholder
            ) ?: ColorDrawable()
            holder.bindDrawable(placeholder)
        }
    }

    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}