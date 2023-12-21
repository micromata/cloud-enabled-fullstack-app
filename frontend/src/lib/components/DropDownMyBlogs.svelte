<script lang="ts">
    import {onDestroy, onMount} from "svelte";
    import {browser} from "$app/environment";

    export let id: number;
    let isMenuOpen;

    const toggleMenu = () => {
        isMenuOpen = !isMenuOpen;
    };

    onMount(() => {
        document.addEventListener("click", handleOutsideClick);

        return () => {
            document.removeEventListener("click", handleOutsideClick);
        };
    });

    const handleOutsideClick = (event: MouseEvent) => {
        const menuButton = document.getElementById(`menu-button-${id}`);

        if (menuButton && !menuButton.contains(event.target as Node)) {
            isMenuOpen = false;
        }
    };
</script>

<div class="relative ml-auto inline-block">
    <div>
        <button type="button" on:click={toggleMenu} class="inline-flex w-full justify-center gap-x-1.5 rounded-full bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50" id={`menu-button-${id}`} aria-expanded={isMenuOpen} aria-haspopup="true">
            Options
            <svg class="-mr-1 h-5 w-5 text-gray-400" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                <path fill-rule="evenodd" d="M5.23 7.21a.75.75 0 011.06.02L10 11.168l3.71-3.938a.75.75 0 111.08 1.04l-4.25 4.5a.75.75 0 01-1.08 0l-4.25-4.5a.75.75 0 01.02-1.06z" clip-rule="evenodd" />
            </svg>
        </button>
    </div>

        <div class:invisible={!isMenuOpen} class="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby={`menu-button-${id}`} tabindex="-1">
            <div class="py-1" role="none">
                <a href="/editFeed?{id}" class="text-gray-700 block px-4 py-2 text-sm hover:bg-gray-100" role="menuitem" tabindex="-1" id={`menu-item-0-${id}`}>Edit Blog</a>
                <a href="#" on:click class="text-gray-700 block px-4 py-2 text-sm hover:bg-gray-100" role="menuitem" tabindex="-1" id={`menu-item-1-${id}`}>Mark as Draft</a>
                <form action="?/delete" method="post">
                    <input type="hidden" name="blogId" value={id} />
                    <button type="submit" class="text-gray-700 text-left block px-4 py-2 text-sm text-red-600 hover:bg-gray-100 w-full" role="menuitem" tabindex="-1" id={`menu-item-2-${id}`}>Delete Blog</button>
                </form>

            </div>
        </div>
</div>
