package edu.tomerbu.lec17.ui.home

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import edu.tomerbu.lec17.R
import edu.tomerbu.lec17.databinding.FragmentHomeBinding
import edu.tomerbu.lec17.ui.adapters.MovieAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.movies.observe(viewLifecycleOwner) {
            val movies = it.map { m -> m.movie }
            val adapter = MovieAdapter(movies) {
                //Toast.makeText(requireContext(), "Clicked: ${it.title}", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putLong("id", it.movieId)

                findNavController().navigate(R.id.action_nav_home_to_detailsFragment, bundle)
            }
            binding.rvMovies.adapter = adapter
            binding.rvMovies.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            binding.rvMovies.scrollToPosition(20)
        }
        homeViewModel.error.observe(viewLifecycleOwner) {
            //if no internet
            binding.cardError.visibility = View.VISIBLE
            binding.textError.text = it
            binding.buttonErrorConfirm.setOnClickListener {
                binding.cardError.visibility = View.GONE
            }
        }

        homeViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressLoading.visibility = View.VISIBLE
            } else {
                binding.progressLoading.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}