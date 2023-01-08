package edu.tomerbu.lec17.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.tomerbu.lec17.databinding.FragmentGalleryBinding
import edu.tomerbu.lec17.ui.adapters.MovieAdapter
import edu.tomerbu.lec17.ui.adapters.MoviesPagingAdapter
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)


        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //paging adapter does not take a list in the constructor:
        val movieAdapter = MoviesPagingAdapter()
        binding.rvPage.adapter = movieAdapter
        binding.rvPage.layoutManager = LinearLayoutManager(requireContext())

        //recycler.adapter = adapter
        galleryViewModel.getMovies().observe(viewLifecycleOwner) {
            //submit the movies to the adapter:
            lifecycleScope.launch {
                movieAdapter.submitData(it)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}