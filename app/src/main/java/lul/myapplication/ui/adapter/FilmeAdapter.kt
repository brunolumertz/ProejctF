package lul.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.filme_item.view.*
import lul.myapplication.R
import lul.myapplication.models.Filme

class FilmeAdapter(
    private val context: Context,
    private val filmes : MutableList<Filme> = mutableListOf(),
) : RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder>(){

//    class FilmeViewHolder(view : View) : RecyclerView.ViewHolder(view){
//        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
////        fun bindFilme(filme : Filme){
////            itemView.filme_titulo.text = filme.tittle
////            itemView.data_lancamento.text = filme.release
////            Glide.with(itemView).load(IMAGE_BASE + filme.poster).into(itemView.filme_poster)
////        }
//    }
aaaa
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.filme_item,
            parent,
            false
        )
        return FilmeViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
//        holder.bindFilme(filmes[position])
        holder.vincula(filmes[position])
    }


    override fun getItemCount(): Int = filmes.size

    fun add(filmes: List<Filme>) {
        this.filmes.clear()
        this.filmes.addAll(filmes)
        notifyDataSetChanged()
    }

    inner class FilmeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var filme: Filme
        private val campoTitulo by lazy { itemView.filme_titulo }
        private val campoData by lazy { itemView.data_lancamento }

//        init {
//            itemView.setOnClickListener {
//                if(::filmes.isInitialized){
//                    onItemClickListener(filmes)
//                }
//            }
//        }


        fun vincula(filme: Filme) {
            this.filme = filme
            campoTitulo.text = filme.tittle
            campoData.text = filme.release
            Toast.makeText(context, filme.tittle, Toast.LENGTH_LONG).show()

        }

    }
}
