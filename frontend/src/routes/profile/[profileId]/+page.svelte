<script>
    import {sanitize} from "isomorphic-dompurify";
    import {auth} from "$lib/user";

    export let data;

</script>

<div class="container bg-white mt-5 mx-auto max-w-5xl p-8">
    <h1 class="font-bold text-2xl">{data.userInfo.name}</h1>
    <div class="grid gap-5 mt-5">
        <div class="flex justify-between">
            <div class="flex self-start">
                <div class="grid pr-3 border-r border-gray-300" style="justify-items: center">
                    <p class="text-xl font-bold">{data.stats.posts}</p>
                    <p class="text-gray-500">Posts</p>
                </div>
                <div class="grid px-3 border-r border-gray-300" style="justify-items: center">
                    <p class="text-xl font-bold">{data.stats.posts}</p>
                    <p class="text-gray-500">Likes</p>
                </div>
                <div class="grid px-2" style="justify-items: center">
                    <p class="text-xl font-bold">{data.stats.follower}</p>
                    <p class="text-gray-500">Follower</p>
                </div>
            </div>

            {#if ($auth)}
                <form class="grid" method="post" action="?/follow">
                    <input type="hidden" name="state" value={!data.following}/>
                    {#if (data.following)}
                        <button type="submit" class="border border-red-500 text-red-500 hover:bg-red-400 hover:text-white transition-colors px-6 rounded disabled:bg-gray-400">Unfollow</button>
                    {:else}
                        <button type="submit" class="bg-blue-500 text-white px-6 hover:bg-blue-400 transition-colors rounded disabled:bg-gray-400">Follow</button>
                    {/if}
                </form>
            {/if}
        </div>

        <div>
            <h2 class="text-xl font-semibold">Bio</h2>
            <p class="text-justify">{@html sanitize(data.bio)}</p>
        </div>

        <div>
            <h2 class="text-xl font-semibold">Blog Posts</h2>
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
    </div>
</div>
