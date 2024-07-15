package it.polito.tdp.itunes.model;

public class Album implements Comparable<Album>{
	private Integer albumId;
	private String title;
	private int nBrani;
	
	public Album(Integer albumId, String title) {
		super();
		this.albumId = albumId;
		this.title = title;
		this.nBrani=0;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getnBrani() {
		return nBrani;
	}

	public void setnBrani(int nBrani) {
		this.nBrani = nBrani;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumId == null) ? 0 : albumId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumId == null) {
			if (other.albumId != null)
				return false;
		} else if (!albumId.equals(other.albumId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public int compareTo(Album other) {
		
		return this.albumId-other.albumId;
	}
	
	
	
}
