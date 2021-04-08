package lul.myapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_minhalista.view.*
import lul.myapplication.R
import lul.myapplication.models.Filme

class FilmeLocalAdapter(
    private val context: Context,
    private val filmes: MutableList<Filme> = mutableListOf(),
    var onClickDeleta: (filme: Filme) -> Unit = {},
    var onClickMarca: (filme: Filme) -> Unit = {}
) : RecyclerView.Adapter<FilmeLocalAdapter.FilmesLocaleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmesLocaleViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_minhalista, parent, false)
        return FilmesLocaleViewHolder(view)
    }

    override fun getItemCount(): Int = filmes.size

    override fun onBindViewHolder(holder: FilmesLocaleViewHolder, position: Int) {
        holder.bind(filmes[position])
    }

    fun append(filmes: List<Filme>) {
        this.filmes.clear()
        this.filmes.addAll(filmes)
        notifyDataSetChanged()
    }

    fun deletaFilme(filme: Filme){
        this.filmes.remove(filme)
        notifyItemRemoved(filmes.indexOf(filme))
    }

    inner class FilmesLocaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        private lateinit var filme: Filme
        private val campoNome by lazy {
            itemView.nome_filme
        }
        private val filmePoster by lazy {
            itemView.filme_poster
        }
        private val filmeDelete by lazy {
            itemView.deleta_filme
        }
        private val filmeMarca by lazy {
            itemView.marca_javi
        }

        init {
            filmeDelete?.let {
                it.setOnClickListener {
                    if(::filme.isInitialized){
                        onClickDeleta(filme)

                    }
                }
            }
        }

        init {
            filmeMarca?.let {
                it.setOnClickListener {
                    if(::filme.isInitialized){
                        onClickMarca(filme)
                    }
                }
            }
        }

        fun bind(filme: Filme) {
            this.filme = filme
            campoNome.text = filme.tittle
            Glide.with(itemView).load(IMAGE_BASE + filme.poster).into(itemView.filme_poster)
        }

    }

}