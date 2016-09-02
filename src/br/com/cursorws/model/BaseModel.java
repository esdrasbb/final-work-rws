package br.com.cursorws.model;

/**
 * Define uma cidade de umestado da federacao.
 * 
 * @author Fabio Barros
 */
public class BaseModel implements Comparable<BaseModel> {
	/**
	 * Identificador da cidade.
	 */
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		return 31 * 1 + ((id == null) ? 0 : id.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BaseModel other = (BaseModel) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
 
	@Override
	public int compareTo(BaseModel obj) {
		if (this.id == null && obj.id == null) {
			return 0;
		}
		if (this.id == null && obj.id != null) {
			return -1;
		}
		if (this.id != null && obj.id == null) {
			return 1;
		}
		return this.id.compareTo(obj.id);
	}

}
