<script lang="ts">

    import {auth} from "$lib/user.ts";
    import {page} from "$app/stores";
    import Alert from "$lib/components/Alert.svelte";

    export let data;
    export let alert:boolean = false;
    function flipFlopAlert() {
        alert = !alert;
    }
</script>
<main class="p-4 relative">
    <div class="max-w-screen-xl mx-auto flex flex-col">
        {#if ($page.url.searchParams.has("created"))}
            <Alert headline="Blog was created successfully!" message="It is now public and anyone can learn from it :)" color="green"/>
        {/if}

        {#if $auth}
        <button class="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-400 ml-auto" onclick="window.location.href='/createFeed';" disabled={!$auth}>
            Create new Blog
        </button>
        {:else}
        <span class="mt-3">
            <Alert headline="Please Log In to Post your Feed!" message="You can Log In " linkText="here." link="/login"/>
        </span>
        {/if}


        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 lg:grid-cols-3 xl:grid-cols-4 gap-4 mt-12 relative">
            {#each data.posts as post (post.id)}
                <div class="flex flex-col rounded-md shadow-md overflow-hidden transition-transform transform hover:scale-105">
                    {#if post.image === null}
                        <div class="bg-gradient-to-br from-blue-500 to-blue-200 h-48"></div>
                    {:else if post.image !== "data:image/png;base64,"}
                        <img class="w-full h-48 object-cover" src={post.image} alt={post.title} />
                    {:else}
                        <div class="bg-gradient-to-br from-blue-500 to-blue-200 h-48"></div>
                    {/if}
                    <div class="p-6 flex-grow grid gap-3">
                        <h2 class="text-xl font-bold">{post.title}</h2>
                        <p class="text-gray-600 overflow-hidden line-clamp-3 overflow-ellipsis">{post.preview}</p>
                        <div class="mt-auto">
                            <a href="/showBlog?id={post.id}" class="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-400">Weiterlesen</a>
                            <!-- Weitere Informationen oder Aktionen können hier hinzugefügt werden -->
                        </div>
                    </div>
                </div>
            {/each}
        </div>
    </div>
</main>


