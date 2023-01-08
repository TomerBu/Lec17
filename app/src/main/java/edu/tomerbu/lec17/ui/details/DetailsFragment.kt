package edu.tomerbu.lec17.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import edu.tomerbu.lec17.R
import edu.tomerbu.lec17.databinding.FragmentDetailsBinding
import edu.tomerbu.lec17.databinding.FragmentHomeBinding

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("id") ?: return

        viewModel.fetchMovieDetails(id)

        viewModel.movieDetails.observe(viewLifecycleOwner) { movie ->
            binding.movieTitle.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate
            binding.movieRating.progress = movie.voteAverage?.toInt() ?: 0
            binding.movieOverview.text = movie.overview
            Picasso.get().load(movie.posterUrl).into(binding.moviePoster)
            Picasso.get().load(movie.backdropUrl).into(binding.movieBackdrop)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}