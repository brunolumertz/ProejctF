package lul.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.filme_item.view.*
import lul.myapplication.R
import lul.myapplication.models.Filme

class FilmeAdapter(
    private val context: Context,
    private val filmes: MutableList<Filme> = mutableListOf(),
    var onItemClickListener: (filme: Filme) -> Unit = {}
) : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.filme_item,
            parent,
            false
        )
        return FilmeViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        holder.vincula(filmes[position])
    }

    override fun getItemCount(): Int = filmes.size

    fun add(filmes: List<Filme>) {
        this.filmes.clear()
        this.filmes.addAll(filmes)
        notifyDataSetChanged()
    }

//    fun addPesquisa(filmes: List<Filme>){
//        this.filmes.clear()
//        this.filmes.addAll(filmes)
//        notifyDataSetChanged()
//    }

    inner class FilmeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        private lateinit var filme: Filme
        private val campoTitulo by lazy { itemView.filme_titulo }
//        private val campoData by lazy { itemView.data_lancamento }

        init {
            itemView.setOnClickListener {
                if (::filme.isInitialized) {
                    onItemClickListener(filme)
//                    Toast.makeText(context, filme.tittle, Toast.LENGTH_LONG).show()
                }
            }
        }


        fun vincula(filme: Filme) {
            this.filme = filme
            campoTitulo.text = filme.tittle
            itemView.filme_titulo.text = filme.tittle
            Glide.with(itemView).load(IMAGE_BASE + filme.poster).into(itemView.filme_poster)
//          campoData.text = filme.release
//          itemView.data_lancamento.text = filme.release
        }

    }
}



