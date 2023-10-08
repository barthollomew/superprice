import { useState, useEffect } from "react";
import axios from "axios";



export default function Items({ initialItems }) {
  const [selectedSupermarket, setSelectedSupermarket] = useState("");
  const [filteredItems, setFilteredItems] = useState(initialItems || []);

  // Function to fetch items based on the selected supermarket
  const fetchItems = () => {
    if (selectedSupermarket) {
      axios
        .get(`http://localhost:8080/v1/items?supermarket=${selectedSupermarket}`)
        .then((response) => {
          setFilteredItems(response.data);
        })
        .catch((error) => {
          console.error("Error fetching items:", error);
        });
    } else {
      setFilteredItems(initialItems);
    }
  };

  // Initial setting of filteredItems
  useEffect(() => {
    setFilteredItems(initialItems);
  }, []);

  return (
    <div className="container mx-auto px-4">
      <h1 className="text-2xl font-bold mb-4">Supermarket Items</h1>
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
      <button
        className="mt-4 mb-4 bg-blue-500 text-white p-2 rounded"
        onClick={fetchItems}
      >
        Submit
      </button>
      <table className="min-w-full bg-white">
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
                  {item.price}
                </td>
                <td className="py-2 px-3 border-b border-gray-200">
                  {item.supermarket}
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
}

export async function getServerSideProps() {
  // Replace with your backend endpoint if different
  const res = await axios.get("http://localhost:8080/v1/items");
  const initialItems = res.data;

  return {
    props: {
      initialItems,
    },
  };
}
