<!-- BlogList.svelte -->

<script>
    import { onMount } from 'svelte';
    import DropDownMyBlogs from "$lib/components/DropDownMyBlogs.svelte";

    export let data;

    let publicBlogs = [];
    let draftBlogs = [];

    onMount(() => {
        // Filtere Blogs nach Status
        publicBlogs = data.filter(blog => blog.status === 'public');
        draftBlogs = data.filter(blog => blog.status === 'draft');
    });

</script>

<div class="flex items-center justify-center h-screen">
    <form method="post" class="w-full max-w-4xl bg-white shadow-md rounded-md p-6">
        <div class="flex">
            <div class="w-1/2 p-4">
                <h2 class="text-2xl font-bold mb-4">Public Blogs</h2>
                <div class="border border-gray-300 p-4 overflow-y-auto rounded-md">
                    {#each publicBlogs as blog (blog.id)}
                        <div class="flex bg-blue-500 text-white my-auto p-2 mb-2 rounded-full">
                            <div class="my-auto ml-2">
                                {blog.title}
                            </div>
                            <DropDownMyBlogs id={blog.id} />
                        </div>
                    {/each}
                </div>
            </div>

            <div class="w-1/2 p-4">
                <h2 class="text-2xl font-bold mb-4">Draft Blogs</h2>
                <div class="border border-gray-300 p-4 overflow-y-auto rounded-md">
                    {#each draftBlogs as blog (blog.id)}
                        <div class="flex bg-gray-500 text-white my-auto p-2 mb-2 rounded-full">
                            <div class="my-auto ml-2">
                                {blog.title}
                            </div>
                            <DropDownMyBlogs id={blog.id} />
                        </div>
                    {/each}
                </div>
            </div>
        </div>
    </form>
</div>