package shopping.list

class ShoppingList {
	String description
	Date dateCreated
	Date lastUpdated
	boolean completed

	static hasMany = [items: ListItem]

    static constraints = {
		description size: 3..64, blank: false, nullable: false
    }
}
