import { useState, useEffect } from "react";
import axios from "axios";
import './ItemsTable.css';

function ItemsTable({ initialItems }) {
  const [selectedSupermarket, setSelectedSupermarket] = useState("");
  const [filteredItems, setFilteredItems] = useState(initialItems || []);
  const [searchQuery, setSearchQuery] = useState("");
  const [sortOrder, setSortOrder] = useState("");
  const [minPrice, setMinPrice] = useState(null);
  const [maxPrice, setMaxPrice] = useState(null);

  const fetchItems = () => {
    const endpoint = `http://localhost:8080/v1/items?supermarket=${selectedSupermarket}&search=${searchQuery}&order=${sortOrder}`;

    axios
      .get(endpoint)
      .then((response) => {
        setFilteredItems(response.data);
      })
      .catch((error) => {
        console.error("Error fetching items:", error);
      });
  };

  useEffect(() => {
    setFilteredItems(initialItems);
  }, [initialItems]);

  return (
    <div className="container mx-auto px-4 flex">
        {/* Filters Sidebar */}
        <div className="w-1/4 pr-8">
            <h2 className="text-xl font-bold mb-4">Filters</h2>

            {/* Search input */}
            <div className="mb-4">
                <label
                  className="block text-sm font-medium text-gray-700"
                  htmlFor="search"
                >
                  Search Product:
                </label>
                <input
                  id="search"
                  className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring focus:border-blue-300"
                  type="text"
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
            </div>

            {/* Supermarket dropdown */}
            <div className="mb-4">
                <label
                  className="block text-sm font-medium text-gray-700"
                  htmlFor="supermarket"
                >
                  Select Supermarket:
                </label>
                <select
                  id="supermarket"
                  className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring focus:border-blue-300"
                  onChange={(e) => setSelectedSupermarket(e.target.value)}
                  value={selectedSupermarket}
                >
                  <option value="">All Supermarkets</option>
                  <option value="Aldi">Aldi</option>
                  <option value="Woolworths">Woolworths</option>
                  <option value="Coles">Coles</option>
                  <option value="IGA">IGA</option>
                </select>
            </div>

            {/* Minimum Price input */}
            <div className="mb-4">
                <label
                  className="block text-sm font-medium text-gray-700"
                  htmlFor="minPrice"
                >
                  Minimum Price:
                </label>
                <input
                  id="minPrice"
                  type="number"
                  onChange={(e) => setMinPrice(e.target.value)}
                />
            </div>

            {/* Maximum Price input */}
            <div className="mb-4">
                <label
                  className="block text-sm font-medium text-gray-700"
                  htmlFor="maxPrice"
                >
                  Maximum Price:
                </label>
                <input
                  id="maxPrice"
                  type="number"
                  onChange={(e) => setMaxPrice(e.target.value)}
                />
            </div>

            {/* Sort order dropdown */}
            <div className="mb-4">
                <label
                  className="block text-sm font-medium text-gray-700"
                  htmlFor="sortOrder"
                >
                  Sort By Price:
                </label>
                <select
                  id="sortOrder"
                  className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring focus:border-blue-300"
                  onChange={(e) => setSortOrder(e.target.value)}
                  value={sortOrder}
                >
                  <option value="">Default</option>
                  <option value="ASC">Lowest to Highest</option>
                  <option value="DESC">Highest to Lowest</option>
                </select>
            </div>

            {/* Submit button */}
            <button
                className="mt-4 bg-blue-500 text-white p-2 rounded w-full"
                onClick={fetchItems}
            >
                Submit
            </button>
        </div>

        {/* Items Table */}
        <div className="w-3/4 bg-blue-100 p-4 rounded-lg shadow-lg">
            <table className="min-w-full bg-white mx-auto">
                <thead>
                  <tr>
                    <th className="py-2 px-3 border-b border-gray-200">Name</th>
                    <th className="py-2 px-3 border-b border-gray-200">Price</th>
                    <th className="py-2 px-3 border-b border-gray-200">Supermarket</th>
                  </tr>
                </thead>
                <tbody>
                  {Array.isArray(filteredItems) &&
                    filteredItems.map((item) => (
                      <tr key={item.id}>
                        <td className="py-2 px-3 border-b border-gray-200">
                          {item.name}
                        </td>
                        <td className="py-2 px-3 border-b border-gray-200">
                          ${item.price.toFixed(2)}
                        </td>
                        <td className="py-2 px-3 border-b border-gray-200">
                          {item.supermarket}
                        </td>
                      </tr>
                    ))}
                </tbody>
            </table>
        </div>
    </div>
  );
}

export default ItemsTable;
