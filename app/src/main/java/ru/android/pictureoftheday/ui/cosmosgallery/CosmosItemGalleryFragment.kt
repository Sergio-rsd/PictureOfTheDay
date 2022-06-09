package ru.android.pictureoftheday.ui.cosmosgallery

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import ru.android.pictureoftheday.R
import ru.android.pictureoftheday.databinding.GalleryItemFragmentBinding

class CosmosItemGalleryFragment : Fragment(R.layout.gallery_item_fragment) {
    private var isFullScreen = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = GalleryItemFragmentBinding.bind(view)
/*
        val imageContainer = view.findViewById<ConstraintLayout>(R.id.container_animate)
        val imagePhoto = view.findViewById<ImageView>(R.id.item_image_universe_show)

        isFullScreen = !isFullScreen

        TransitionManager.beginDelayedTransition(
            imageContainer, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
                .setDuration(3000)
        )
        val dimensionWight =ViewGroup.LayoutParams.MATCH_PARENT
//            if (isFullScreen) ViewGroup.LayoutParams.MATCH_PARENT else resources.getDimension(
//                R.dimen.image_item_gallery
//            ).toInt()
        val dimensionHeight = ViewGroup.LayoutParams.WRAP_CONTENT
//            if (isFullScreen) ViewGroup.LayoutParams.WRAP_CONTENT else resources.getDimension(
//                R.dimen.image_item_gallery
//            ).toInt()
        val scaleType = ImageView.ScaleType.FIT_CENTER
//            if (isFullScreen) ImageView.ScaleType.FIT_CENTER else ImageView.ScaleType.FIT_XY

        imagePhoto.updateLayoutParams {
            width = dimensionWight
            height = dimensionHeight
        }
        imagePhoto.scaleType = scaleType
  */


        val imageClickable = view.findViewById<ImageView>(R.id.item_image_universe_show)
        val animeScaleX = ObjectAnimator.ofFloat(view, "scaleX", 3.3f)
//        val animeScaleX = ObjectAnimator.ofFloat(view, "scaleX", ViewGroup.LayoutParams.MATCH_PARENT.toFloat())
//        val animeScaleX = ObjectAnimator.ofFloat(view, "scaleX", view.width.toFloat())
        val animeScaleY = ObjectAnimator.ofFloat(view, "scaleY", 3.3f)
//        val animeScaleY = ObjectAnimator.ofFloat(view, "scaleY", ViewGroup.LayoutParams.WRAP_CONTENT.toFloat())
        AnimatorSet().apply {
            playTogether(animeScaleX, animeScaleY)
            duration = 1000
            start()
        }


        imageClickable.setOnClickListener {
//        imageClickable.setOnClickListener {

//            val imageClickable = view.findViewById<ImageView>(R.id.item_image_universe_show)
            val animeScaleXDown = ObjectAnimator.ofFloat(view, "scaleX", 1f)
            val animeScaleYDown = ObjectAnimator.ofFloat(view, "scaleY", 1f)
            val changeScale = AnimatorSet().apply {
                playTogether(animeScaleXDown, animeScaleYDown)
            }
            val fadeAnime = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {
                duration = 300
            }
            AnimatorSet().apply {
//                playTogether(animeScaleX, animeScaleY)
                play(changeScale).before(fadeAnime)
                duration = 1000
                doOnEnd {
                    requireActivity().supportFragmentManager
                        .popBackStack()
                }
                start()
            }
        }
    }
}