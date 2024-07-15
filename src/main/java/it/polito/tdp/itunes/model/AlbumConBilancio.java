package it.polito.tdp.itunes.model;

import java.util.Objects;

public class AlbumConBilancio {
	
	private Album album;
	private int bilancio;
	
	
	public AlbumConBilancio(Album album, int bilancio) {
		super();
		this.album = album;
		this.bilancio = bilancio;
	}


	public int getBilancio() {
		return bilancio;
	}


	public void setBilancio(int bilancio) {
		this.bilancio = bilancio;
	}


	@Override
	public int hashCode() {
		return Objects.hash(album);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlbumConBilancio other = (AlbumConBilancio) obj;
		return Objects.equals(album, other.album);
	}


	@Override
	public String toString() {
		return "" + album.getTitle() + ", bilancio=" + bilancio;
	}
	
	

}
