// pages/index.js
import axios from 'axios';

import Header from './components/header';
import Footer from './components/footer';
import ItemsTable from './components/ItemsTable';
function Home({ initialItems }) {
  return (
      <div className="p-4">
          <Header />
          <ItemsTable initialItems={initialItems} />
          <Footer />
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

export default Home;

