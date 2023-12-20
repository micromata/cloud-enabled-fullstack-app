<script lang="ts">
    import { page } from '$app/stores';

    export let data;

    let selectedPost: any;

    $: selectedPost = data.posts.find(post => post.id == $page.url.searchParams.get('id'));
</script>

    {#if selectedPost}
<div class="container mx-auto p-8">
    {#if selectedPost.image}
    <img class="w-full h-80 object-cover mb-4 max-w-xl mx-auto" src={selectedPost.image} alt={selectedPost.title}>
    {:else}
        <div></div>
    {/if}
    <h1 class="text-4xl font-bold mb-2">{selectedPost.title}</h1>
    <p class="text-gray-600 mb-4">Date: XX.XX.20XX</p>
    <div class="prose max-w-full">
        <!-- Blog content goes here -->
        <p>{selectedPost.description}</p>
    </div>
</div>
{:else}
<p>No blog post found for ID {$page.url.searchParams.get('id')}</p>
{/if}