package shopping.list

class ListItem {
	String name
	int quantity
	boolean obtained

	static belongsTo = ShoppingList

    static constraints = {
		name size: 3..64, blank: false, nullable: false
		quantity min: 1, nullable: false
    }
}
