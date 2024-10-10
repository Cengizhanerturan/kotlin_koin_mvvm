package com.gcyazilim.koincryptocrazy.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gcyazilim.koincryptocrazy.core.model.CryptoModel
import com.gcyazilim.koincryptocrazy.core.util.Resource
import com.gcyazilim.koincryptocrazy.databinding.FragmentListBinding
import com.gcyazilim.koincryptocrazy.view.adapter.RecyclerViewAdapter
import com.gcyazilim.koincryptocrazy.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class ListFragment : Fragment(), RecyclerViewAdapter.Listener {
    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var cryptoAdapter = RecyclerViewAdapter(arrayListOf(), this)
    private val viewModel by viewModel<CryptoViewModel>()

    /*
    private val api = get<Api>()
    private val apiLazy by inject<Api>()
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        viewModel.getDataFromAPI()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.cryptoList.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.cryptoProgressBar.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE

                    cryptoAdapter = RecyclerViewAdapter(
                        ArrayList(resource.data),
                        this@ListFragment
                    )
                    binding.recyclerView.adapter = cryptoAdapter
                }

                is Resource.Error -> {
                    binding.cryptoErrorText.visibility = View.VISIBLE
                    binding.cryptoProgressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                }

                is Resource.Loading -> {
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(requireContext(), "Clicked on: ${cryptoModel.currency}", Toast.LENGTH_LONG)
            .show()
    }
}