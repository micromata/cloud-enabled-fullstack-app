<script lang="ts">
    import {page} from '$app/stores';
    import {sanitize} from "isomorphic-dompurify";
    import {auth} from "$lib/user";
    import img from "$lib/assets/EditIcon.png"
    import Input from "$lib/components/Input.svelte";

    export let data;

    let selectedPost: any;

    $: selectedPost = data

    $: console.log(selectedPost, $auth.id);
</script>

{#if selectedPost}
    <div class="container bg-white rounded-md mt-5 mx-auto max-w-5xl p-8">
        {#if selectedPost.image === null}
            <div class="bg-gradient-to-br from-blue-500 to-blue-200 h-80 rounded-md"></div>
        {:else if selectedPost.image !== "data:image/png;base64,"}
            <img class="w-full h-80 object-cover mb-4 rounded-md" src={selectedPost.image} alt={selectedPost.title}>
        {:else}
            <div class="bg-gradient-to-br from-blue-500 to-blue-200 h-80 rounded-md"></div>
        {/if}

        <div class="flex justify-between items-center mb-4">
            <h1 class="text-4xl font-bold">{selectedPost.title}</h1>
            {#if selectedPost.user.id == $auth.id}
                <a href="/editFeed?id={selectedPost.id}">
                    <img width="16" src={img} alt="Edit your Feed">
                </a>
            {/if}
        </div>

        <p class="text-gray-600 mb-4">Date: {new Date(selectedPost.timestamp).toLocaleDateString()}</p>
        <div class="prose max-w-full">
            <p>{@html sanitize(selectedPost.description)}</p>
        </div>
    </div>
{:else}
    <div class="container bg-white rounded-md mt-5 mx-auto max-w-5xl p-8">
    <p>No blog post found for ID {$page.url.searchParams.get('id')}</p>
    </div>
{/if}
<div class="container bg-white rounded-md mt-5 mx-auto max-w-5xl p-8">
    <h1 class="text-2xl font-bold pb-2">Comments</h1>
    <form method="post">
        <Input label="Your Comment" name="comment" type="longtext"/>
    </form>

</div>
