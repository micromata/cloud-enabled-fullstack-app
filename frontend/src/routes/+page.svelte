<script lang="ts">
    import {auth} from "$lib/user";

    export let data;
</script>


<div class="flex items-center pb-80 justify-center h-screen">
    <div class="w-full max-w-7xl bg-white shadow-md rounded-md p-6">
        {#if $auth}
        <h1 class="text-4xl font-bold mb-8">Welcome, {$auth.username}, To Cosmos Connect!</h1>
            {:else }
            <h1 class="text-4xl font-bold mb-8">Welcome, Visitor, To Cosmos Connect!</h1>
        {/if}

        <section>
            <h2 class="text-2xl font-bold mb-4">About Cosmos Connect</h2>
            <p class="text-gray-600">
                Welcome to Cosmos Connect, where the power of connection meets the pursuit of a better world. At Cosmos Connect, we believe in the transformative potential of human connection, and our mission is to bring people together to share information, insights, and knowledge with the goal of making the world a better place.
            </p>

            <h2 class="text-xl font-bold mb-2 mt-3">Our Vision</h2>
            <p class="text-gray-600">
                Our vision is a world where individuals from diverse backgrounds, experiences, and perspectives come together to exchange ideas and learn from one another. We envision a platform where the collective wisdom of our global community fuels positive change and inspires individuals to contribute to the greater good.
            </p>

            <h2 class="text-xl font-bold mb-2 mt-3">Building Connections</h2>
            <p class="text-gray-600">
                Cosmos Connect serves as a hub for individuals seeking to connect with others who share similar passions, interests, or goals. Whether you're an expert in a particular field or someone eager to learn, our platform provides a space for meaningful interactions and collaborations. Through these connections, we strive to foster an environment where knowledge and experiences are freely shared, creating a ripple effect of positive change.
            </p>

            <h2 class="text-xl font-bold mb-2 mt-3">Learning and Growing Together</h2>
            <p class="text-gray-600">
                Learning is at the heart of Cosmos Connect. We believe that the exchange of information is a catalyst for personal and societal growth. By facilitating discussions, sharing valuable resources, and providing a platform for collaborative learning, we empower our community members to expand their horizons and contribute to the collective wisdom of humanity.
            </p>

            <h2 class="text-xl font-bold mb-2 mt-3">Commitment to Data Security</h2>
            <p class="text-gray-600">
                At Cosmos Connect, your privacy and data security are paramount. We understand the importance of trust in building meaningful connections, and we are dedicated to safeguarding your information. Our platform employs state-of-the-art security measures to ensure that your data is protected, allowing you to engage in open and honest conversations without compromising your privacy.
            </p>

            <h2 class="text-xl font-bold mb-2 mt-3">Join Us in Shaping a Better Future</h2>
            <p class="text-gray-600">
                Cosmos Connect is more than a platform; it's a community-driven initiative to make a positive impact on the world. Join us on this journey of connection, collaboration, and continuous learning. Together, we can shape a better future—one where the exchange of knowledge transcends borders and brings people together for the greater good.
            </p>

            <p class="text-gray-600">
                Thank you for being a part of Cosmos Connect. Together, let's connect, learn, and inspire positive change.
            </p>
        </section>
    </div>
</div>
