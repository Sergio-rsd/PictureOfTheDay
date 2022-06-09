package ru.android.pictureoftheday.ui.cosmosgallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.databinding.GalleryFragmentBinding
import ru.android.pictureoftheday.viewmodel.gallery.GalleryViewModel

class CosmosGalleryFragment : Fragment(R.layout.gallery_fragment) {

    companion object {
        private const val START_DATE = "START_DATE"
        private const val END_DATE = "END_DATE"
        fun newInstance(start_date: String, end_date: String) = CosmosGalleryFragment().apply {
            arguments = bundleOf(START_DATE to start_date, END_DATE to end_date)
        }
    }

    private val recycler: RecyclerView by lazy {
        requireActivity().findViewById(R.id.recycler_gallery)
    }
    private val viewModel: GalleryViewModel by activityViewModels()

    private var isFullScreen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startDate = arguments?.getString(START_DATE)
        val endDate = arguments?.getString(END_DATE)

        val binding = GalleryFragmentBinding.bind(view)

//        val rootRecycle = binding.root
//        recycler.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler.adapter = GridAdapter()

    }

    inner class GridAdapter : RecyclerView.Adapter<GridAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            )
        }

        override fun getItemCount(): Int = 30

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val imagePhoto = view.findViewById<ImageView>(R.id.item_image_universe)
            private val imageContainer = view.findViewById<FrameLayout>(R.id.image_container)

            init {
                imagePhoto.setOnClickListener {

//                    Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
/*
                    val imageClickable = view.findViewById<ImageView>(R.id.item_image_universe_show)
                    val animScaleX = ObjectAnimator.ofFloat(view, "scaleX", 3.3f)
                    val animScaleY = ObjectAnimator.ofFloat(view, "scaleY", 3.3f)
                    AnimatorSet().apply {
                        playTogether(animScaleX,animScaleY)
                        duration = 1000
                        start()
                    }

                    */

                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.container, CosmosItemGalleryFragment())
                        .addToBackStack("")
                        .commit()
/*

                    isFullScreen = !isFullScreen

                    TransitionManager.beginDelayedTransition(
                        imageContainer, TransitionSet()
                            .addTransition(ChangeBounds())
                            .addTransition(ChangeImageTransform())
                            .setDuration(3000)

                    )
                    val dimensionWight =
                        if (isFullScreen) ViewGroup.LayoutParams.MATCH_PARENT else resources.getDimension(
                            R.dimen.image_item_gallery
                        ).toInt()
                    val dimensionHeight =
                        if (isFullScreen) ViewGroup.LayoutParams.WRAP_CONTENT else resources.getDimension(
                            R.dimen.image_item_gallery
                        ).toInt()
                    val scaleType =
                        if (isFullScreen) ImageView.ScaleType.FIT_CENTER else ImageView.ScaleType.FIT_XY

                    imagePhoto.updateLayoutParams {
                        width = dimensionWight
                        height = dimensionHeight
                    }
                    imagePhoto.scaleType = scaleType

*/

//                    Toast.makeText( requireContext(),"Click", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            TODO("Not yet implemented")
        }
    }

}


