<script lang="ts">
    import {page} from '$app/stores';

    export let data;

    let selectedPost: any;

    $: selectedPost = data

    console.log(selectedPost);
</script>

    {#if selectedPost}
<div class="container mx-auto max-w-5xl p-8">
    {#if selectedPost.image === null}
        <div class="bg-gradient-to-br from-blue-500 to-blue-200 h-80 rounded-md"></div>
    {:else if selectedPost.image !== "data:image/png;base64,"}
        <img class="w-full h-80 object-cover mb-4 max-w-xl mx-auto rounded-md" src={selectedPost.image} alt={selectedPost.title}>
    {:else}
        <div class="bg-gradient-to-br from-blue-500 to-blue-200 h-80 rounded-md"></div>
    {/if}
    <h1 class="text-4xl font-bold mb-2">{selectedPost.title}</h1>
    <p class="text-gray-600 mb-4">Date: {new Date(selectedPost.timestamp).toLocaleDateString()}</p>
    <div class="prose max-w-full">
        <!-- Blog content goes here -->
        <p>{selectedPost.description}</p>
    </div>
</div>
{:else}
<p>No blog post found for ID {$page.url.searchParams.get('id')}</p>
{/if}