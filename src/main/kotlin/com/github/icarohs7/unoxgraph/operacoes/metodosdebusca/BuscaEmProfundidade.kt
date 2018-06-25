package com.github.icarohs7.unoxgraph.operacoes.metodosdebusca

import com.github.icarohs7.unoxgraph.Grafo
import com.github.icarohs7.unoxgraph.estatico.CaminhoNaoEncontradoException
import com.github.icarohs7.unoxgraph.estatico.INFINITO
import java.util.Stack

object BuscaEmProfundidade {
	
	@Suppress("NAME_SHADOWING")
	fun buscar(origem: Int, destino: Int, grafo: Grafo): List<Int> {
		/* Desmarcar todos os nós visitados */
		grafo.inicializar()
		
		/* Instanciar a pilha utilizadas no processo de busca */
		val caminhoInvertido = Stack<Int>()
		
		/* Lançar uma exceção caso o destino não seja encontrado */
		if (!dfs(origem, destino, grafo, caminhoInvertido)) {
			throw CaminhoNaoEncontradoException()
		}
		
		/* Retornar o caminho da origem para o destino */
		return caminhoInvertido
	}
	
	/**
	 * Procedimento recursivo responsável pela busca em profundidade
	 * @param origem Int: O nó a partir do qual a busca será iniciada
	 * @param destino Int: O nó que se deseja encontrar partindo da origem
	 * @param grafo Grafo: Grafo utilizado
	 * @param caminhoInvertido Stack<Int>: Pilha utilizada para armazenar o caminho
	 * @return Boolean: Verdadeiro se o caminho for encontrado e Falso caso contrário
	 */
	private fun dfs(origem: Int, destino: Int, grafo: Grafo, caminhoInvertido: Stack<Int>): Boolean {
		/* Marcar o vértice atual */
		grafo.visitados[origem] = true
		
		/* adicioná-lo à pilha */
		caminhoInvertido.push(origem)
		
		/* e retornar caso o destino tenha sido alcançado */
		if (origem == destino) {
			return true
		}
		
		grafo.matrizDeAdjacencia.indices.forEach { i ->
			/* Para cada vértice adjacente */
			if (grafo.matrizDeAdjacencia[origem][i] != INFINITO) {
				/* e não visitado */
				if (!grafo.visitados[i]) {
					/* descer um nível na busca */
					if (dfs(i, destino, grafo, caminhoInvertido)) {
						/* e voltar caso o destino seja encontrado em uma das chamadas recursivas */
						return true
					}
				}
			}
		}
		
		//		/* Desempilhar os vértices que levam ao caminho incorreto */
		//		caminhoInvertido.pop()
		
		/* e retornar avisando que o destino não foi encontrado no galho recursivo atual */
		return false
	}
}