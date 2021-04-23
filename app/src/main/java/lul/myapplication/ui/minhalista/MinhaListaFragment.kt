package lul.myapplication.ui.minhalista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_minhalista.*
import lul.myapplication.util.Constantes.Companion.JAVI
import lul.myapplication.util.Constantes.Companion.QUEROVER
import lul.myapplication.R
import lul.myapplication.ui.adapter.TabelaAdapter
import lul.myapplication.ui.minhalista.tabelas.JaViFragment
import lul.myapplication.ui.minhalista.tabelas.QueroVerFragment

class MinhaListaFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_minhalista,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configuraTabelas()
    }

    private fun configuraTabelas() {
        val adapter = TabelaAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(JaViFragment(), JAVI)
        adapter.addFragment(QueroVerFragment(), QUEROVER)

        view_pager.adapter = adapter
        tab_minhalista.setupWithViewPager(view_pager)
    }

//    private fun configuraTabListas(view: View){
//        val toolbar = view.findViewById<MaterialToolbar>(R.id.)
//    }
}