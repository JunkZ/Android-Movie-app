package com.ltu.m7019edemoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.ltu.m7019e.m7019e_moviedbapp.viewmodel.MovieDetailViewModel
import com.ltu.m7019e.m7019e_moviedbapp.viewmodel.MovieDetailViewModelFactory
import com.ltu.m7019edemoapp.database.Genre
import com.ltu.m7019edemoapp.databinding.FragmentMovieDetailBinding
import com.ltu.m7019edemoapp.databinding.FragmentMovieListBinding
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.viewmodel.MovieListViewModel
import com.ltu.m7019edemoapp.viewmodel.MovieListViewModelFactory

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var movie: Movie
    ///private lateinit var movieDet: MovieDetail
    private lateinit var homepage: String
    private lateinit var imbdId: String

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var viewModelFactory: MovieDetailViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        val application = requireNotNull(this.activity).application
        binding.movie = movie
//        var Genre: Genre = Genre("")
//        if (movie.title == "Raya and the Last Dragon") {
//            Genre = Genre("Fantasy")
//        } else if (movie.title == "Sentinelle") {
//            Genre = Genre("Action")
//        } else if (movie.title == "Zack Snyder's Justice League"){
//            Genre = Genre("Action")
//        } else if (movie.title == "Tom & Jerry"){
//            Genre = Genre("Comedy")
//        } else if (movie.title == "Parasite"){
//            Genre = Genre("Thriller")
//        }
//        binding.genre =  Genre

        viewModelFactory = MovieDetailViewModelFactory(movie.id,application,movie)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MovieDetailViewModel::class.java)

        viewModel.movieHomepage.observe(viewLifecycleOwner, { homepage1 ->
            homepage = homepage1
            //binding.movieDetailHomepage.text = "Homepage: $homepage"
        })

        viewModel.movieImdbId.observe(viewLifecycleOwner, { imdb_id ->
            imbdId = imdb_id
            //binding.movieDetailImdbId.text = "IMDB Page: " + Constants.IMDB_BASE_URL + imdb_id
        })

        //}
        //movieDet = MovieDetailFragmentArgs.fromBundle(requireArguments()).movieDetail
        //binding.movieDetail = movieDet
        //val movies = Movies()
        //binding.movie = movies



        //val movieDet = MovieDetails()

        //val movieDet = MovieDetailFragmentArgs.fromBundle(requireArguments()).movieDetail
        //binding.movieDetail = movieDet

        //movieDetails = MovieDetailFragmentArgs.fromBundle(requireArguments()).movieDetail
        //binding.movieDetails = movieDetails
        setHasOptionsMenu(true)
        return binding.root

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
        //return NavigationUI.onNavDestinationSelected(item,view!!.findNavController())
        //        ||super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //..bad way to do it, too many findviewbyid (happens during runtime)
        //val movieDet = MovieDetails()
        //val movieGenreList = view.findViewById<ConstraintLayout>(R.id.genreFrag)
        //val movieGenre = movieGenreList.findViewById<TextView>(R.id.genreAPI)
        //movieGenre.text = movieDet.list[0].genre


        binding.movieDetailToMovieListButtonView.setOnClickListener {
            findNavController().navigate(R.id.action_MovieDetailFragment_to_MovieListFragment)
        }
        binding.buttonThird.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionMovieDetailFragmentToThirdFragment(movie))
        }
        binding.linkButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(homepage)
            startActivity(intent)
        }

        binding.IDBMbutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.imdb.com/title/" + imbdId)
            startActivity(intent)
        }
//        binding.linkButton.setOnClickListener {
//            ShareIntentWebLink()
//        }
//        binding.IDBMbutton.setOnClickListener {
//            ShareIntentIMBDLink()
//        }
    }
//
//    private fun ShareIntentWebLink() : Intent {
//        //val openURL = Intent(android.content.Intent.ACTION_VIEW)
//        //openURL.data = Uri.parse("https://www.tutorialkart.com/")
//
//        //val args = GameWonFragmentArgs.fromBundle(requireArguments())
//        val url = movie.
//        val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(shareIntent)
//
//        return shareIntent
//    }
//    private fun ShareIntentIMBDLink() : Intent {
//        //val openURL = Intent(android.content.Intent.ACTION_VIEW)
//        //openURL.data = Uri.parse("https://www.tutorialkart.com/")
//
//        //val args = GameWonFragmentArgs.fromBundle(requireArguments())
//        val url = "https://www.imdb.com/title/"+movie.imdb_id
//        val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(shareIntent)
//
//        return shareIntent
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}