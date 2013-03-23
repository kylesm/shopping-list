package shopping.list

class ListItem implements Comparable {
	String name
	int quantity
	boolean obtained
	Integer order = 1

	static belongsTo = ShoppingList

    static constraints = {
		name size: 3..64, blank: false, nullable: false
		quantity min: 1, nullable: false
    }

	static mapping = {
		order column: "item_order"
	}

	int compareTo(other) {
		return order.compareTo(other.order)
	}
}
