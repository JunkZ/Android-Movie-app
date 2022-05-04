package com.ltu.m7019edemoapp

import android.content.ContentValues.TAG
import android.net.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ltu.m7019edemoapp.adapter.MovieListAdapter
import com.ltu.m7019edemoapp.adapter.MovieListClickListener
import com.ltu.m7019edemoapp.database.getDatabase
import com.ltu.m7019edemoapp.databinding.FragmentMovieListBinding
import com.ltu.m7019edemoapp.viewmodel.MovieListViewModel
import com.ltu.m7019edemoapp.viewmodel.MovieListViewModelFactory
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private lateinit var viewModelFactory: MovieListViewModelFactory

    private var _binding: FragmentMovieListBinding? = null

    private var lastSelected: MenuItem? = null
    private var online = false






    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        viewModelFactory = MovieListViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MovieListViewModel::class.java)


        val connectivityManager = getSystemService(application.applicationContext,ConnectivityManager::class.java)
        val currentNetwork = connectivityManager?.getActiveNetwork()
        val caps = connectivityManager?.getNetworkCapabilities(currentNetwork)
        val linkProperties = connectivityManager?.getLinkProperties(currentNetwork)

        val videosRepository = MovieResponseRepository(getDatabase(application))

        //source: https://developer.android.com/training/basics/network-ops/reading-network-state
        connectivityManager?.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network : Network) {
                Log.e(TAG, "The default network is now: " + network)
                online = true
                lastSelected?.let { onOptionsItemSelected(it) }
            }

            override fun onLost(network : Network) {
                online = false
                Log.e(TAG, "The application no longer has a default network. The last default network was " + network)
            }

            override fun onCapabilitiesChanged(network : Network, networkCapabilities : NetworkCapabilities) {
                Log.e(TAG, "The default network changed capabilities: " + networkCapabilities)
            }

            override fun onLinkPropertiesChanged(network : Network, linkProperties : LinkProperties) {
                Log.e(TAG, "The default network changed link properties: " + linkProperties)
            }
        })

        val movieListAdapter = MovieListAdapter(
            MovieListClickListener { movie ->
                Log.i("Obs","Adapter")
                viewModel.movieClicked(movie)
            })
        //val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        //val movieListAdapter = MovieListAdapter()
        binding.listRv.adapter = movieListAdapter
        //binding.listRv.layoutManager = manager
        viewModel.movieList.observe(
            viewLifecycleOwner
        ) { movieList ->
            Log.i("Obs", "Observer movie list")
            movieListAdapter.submitList(movieList)
        }
        viewModel.ToMovieDetail.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                Log.i("Obs", "Observer movie detail")
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(movie)
                )
                viewModel.gotoDetails()
            }
        }


//        viewModel.movieList.observe(
//            viewLifecycleOwner, {movieList ->
//                movieList?.let {
//                    Log.i("Obs","Observer triggered!")
//                    movieListAdapter.submitList(movieList)
//                }
//            }
//        )
//            movieList.forEach { movie ->
//                val movieListItemBinding: MovieListItemSmallBinding = DataBindingUtil.inflate(
//                    inflater,
//                    R.layout.movie_list_item_small,
//                    container,
//                    false
//                )
//                movieListItemBinding.movie = movie
//                movieListItemBinding.root.setOnClickListener {
//                    this.findNavController().navigate(
//                        MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
//                            movie
//                        )
//                    )
//                }
//                binding.listCv.addView(movieListItemBinding.root)


        setHasOptionsMenu(true)


        //viewModelFactory = MovieListViewModelFactory
        //viewModel = ViewModelProvider(this,viewModelFactory).get(MovieListViewModel::class.java)

        //binding.listGl.adapter = movieListAdapter
        //binding.sleepList.layoutManager = manager
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        return NavigationUI.
////        onNavDestinationSelected(item,requireView().findNavController())
////                || super.onOptionsItemSelected(item)
////    }
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    when (item.itemId) {
        R.id.action_load_popular_movies -> {
            Log.i("Option","Popular movies")
            lastSelected = item
            viewModel.refreshDataFromRepository(true)
            viewModel.getPopularMovies()
            checkInternet()
        }
        R.id.action_load_top_rated_movies -> {
            lastSelected = item
            viewModel.refreshDataFromRepository(false)
            viewModel.getTopRatedMovies()
            checkInternet()
        }
        else -> super.onOptionsItemSelected(item)
    }
    return true
}
    fun checkInternet() {
        if (online) {
            binding.errorImage.visibility= View.INVISIBLE
        } else {
            binding.errorImage.visibility = View.INVISIBLE
            binding.loadingText.visibility = View.VISIBLE

            Handler().postDelayed({
                binding.loadingText.visibility = View.INVISIBLE
                binding.errorImage.visibility = View.VISIBLE
            }, 3000)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//class NetworkChangeReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d("NetworkChangeReceiver", "Connection status changed")
//        observable!!.connectionChanged()
//    }
//
//    class NetworkObservable private constructor() : Observable() {
//        fun connectionChanged() {
//            setChanged()
//            notifyObservers()
//        }
//
//        companion object {
//            var instance: NetworkObservable? = null
//                get() {
//                    if (field == null) {
//                        field = NetworkObservable()
//                    }
//                    return field
//                }
//                private set
//        }
//    }
//
//    companion object {
//        val observable: NetworkObservable?
//            get() = NetworkObservable.instance
//    }
//}
