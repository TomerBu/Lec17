package edu.tomerbu.lec17.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import edu.tomerbu.lec17.databinding.MovieItemBinding
import edu.tomerbu.lec17.models.Movie

//Recycler view adapter + diff util:
//helper methods for checking identity / equality
class MoviesPagingAdapter : PagingDataAdapter<Movie, MoviesPagingAdapter.VH>(diffCallback) {

    //static member:
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val movie = getItem(position)
        with(holder.binding) {
            tvTitle.text = movie?.title
            Picasso.get().load(movie?.posterUrl).into(imagePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return VH(binding)
    }


    class VH(val binding: MovieItemBinding) :
        ViewHolder(binding.root)

}