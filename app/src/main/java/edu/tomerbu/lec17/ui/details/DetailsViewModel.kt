package edu.tomerbu.lec17.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.tomerbu.lec17.MoviesApplication
import edu.tomerbu.lec17.models.MovieDetailsResponse
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    //private (mutable)
    private val _movieDetails: MutableLiveData<MovieDetailsResponse> = MutableLiveData()

    //public (immutable)
    public val movieDetails: LiveData<MovieDetailsResponse> = _movieDetails

    fun fetchMovieDetails(id: Long) {
        viewModelScope.launch {
            val response = MoviesApplication.tmdbService.getMovieDetails(id)
            _movieDetails.postValue(response)
        }
    }
}