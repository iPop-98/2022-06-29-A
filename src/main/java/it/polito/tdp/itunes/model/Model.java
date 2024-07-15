package it.polito.tdp.itunes.model;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Album, DefaultWeightedEdge> grafo;
	private Map<Integer, Album> albumIdMap;

	public Model() {
		this.dao = new ItunesDAO();
		this.albumIdMap = new HashMap<Integer, Album>();
		this.initializeIdMap();
	}
	
	
	private void initializeIdMap() {
		List<Album> albums = this.dao.getAllAlbums();
		for(Album a : albums)
			this.albumIdMap.put(a.getAlbumId(), a);
		
	}


	public boolean creaGrafo(int n) {
		this.grafo = new SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//Aggiunta dei vertici, leggendo dal DB
		List<Album> allVertex = this.dao.getVertex(n, this.albumIdMap);
		Graphs.addAllVertices(this.grafo, allVertex);
		
		//procediamo con l'aggiunta degli archi
		int delta = 0;
		for(Album a1 : this.grafo.vertexSet())
			for(Album a2 : this.grafo.vertexSet())
				if(a1.compareTo(a2)>0) {
					delta = a1.getnBrani()-a2.getnBrani();
					if(delta >0)
						Graphs.addEdge(this.grafo, a2, a1, delta);
					else if(delta < 0)
						Graphs.addEdge(this.grafo, a1, a2, -delta);
				}
					
		return (this.grafo.vertexSet().size()>0);
	}


	public List<Album> getVertex() {
		List<Album> verticiPerTitolo = new LinkedList<>();
		verticiPerTitolo.addAll(this.grafo.vertexSet());
		verticiPerTitolo.sort(new Comparator<Album>(){
								public int compare(Album a1, Album a2) {
									return a1.getTitle().compareTo(a2.getTitle());
								}
							});
		return verticiPerTitolo;
	}
	
	
	public Set<DefaultWeightedEdge> getEdges() {
		return this.grafo.edgeSet();
	}


	public List<AlbumConBilancio> getAdiacenze(Album vertice) {
		
		List<AlbumConBilancio> adiacenti = new LinkedList<>();
		for(Album a : Graphs.successorListOf(this.grafo, vertice))
			adiacenti.add(new AlbumConBilancio(a, this.calcolaBilancio(a)));
		
		adiacenti.sort(new Comparator<AlbumConBilancio>(){
		
							public int compare(AlbumConBilancio a1, AlbumConBilancio a2) {
								return a2.getBilancio()-a1.getBilancio();
							}
							
						});
		
		return adiacenti;
	}
	
	private Integer calcolaBilancio(Album a1) {
		int bilancio = 0;
//		System.out.println(" Archi entranti in " + a1.toString() + "\n");
		for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(a1)) {
			bilancio += this.grafo.getEdgeWeight(e);
//			System.out.println(e+"\n");
		}
		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(a1)) {
			bilancio -= this.grafo.getEdgeWeight(e);
//			System.out.println(e+"\n");
		}
		
		return bilancio;
		
	}


	private List<Album> migliore;
	private int verticiMaggioreMigliore;
	private int bilancioSoglia;
	private int pesoSoglia;
	
	public List<Album> trovaPercorso(Album a1, Album a2, int x) {
		this.bilancioSoglia = this.calcolaBilancio(a1);
		this.pesoSoglia = x;
		this.verticiMaggioreMigliore=0;
		this.migliore = new ArrayList<Album>();
		
		List<Album> parziale = new ArrayList<>();
		parziale.add(a1);
		this.cerca(parziale, 0, a2);
		return this.migliore;
	}
	
	private void cerca(List<Album> parziale, int verticiMaggiore, Album arrivo) {
		Album current = parziale.get(parziale.size()-1);
		
		if(current.equals(arrivo)) {
			if(verticiMaggiore>this.verticiMaggioreMigliore) {
				this.verticiMaggioreMigliore = verticiMaggiore;
				this.migliore = new ArrayList<Album>(parziale);
				return;
			}
		}
		for(Album a : Graphs.successorListOf(this.grafo, current)) {
			if(!parziale.contains(a)) {
				DefaultWeightedEdge edge = this.grafo.getEdge(current, a);
				double peso = this.grafo.getEdgeWeight(edge);
				if(peso>=this.pesoSoglia) {
					parziale.add(a);
					if(this.calcolaBilancio(a)>this.bilancioSoglia) 
						this.cerca(parziale, verticiMaggiore+1, arrivo);
					else
						this.cerca(parziale, verticiMaggiore, arrivo);
					parziale.remove(parziale.size()-1); //backtraking
				}
			}
		}
	}
}
