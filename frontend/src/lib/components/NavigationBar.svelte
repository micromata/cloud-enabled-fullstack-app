<script lang="ts">
    import {auth} from "$lib/user.js";
    import {onMount} from "svelte";

    let isMenuOpen = false;

    const toggleMenu = () => {
        isMenuOpen = !isMenuOpen;
    };

    onMount(() => {
        // Event-Listener hinzufügen, um das Dropdown zu schließen
        document.addEventListener("click", handleOutsideClick);

        // Aufräumen: Event-Listener entfernen, wenn das Component zerstört wird
        return () => {
            document.removeEventListener("click", handleOutsideClick);
        };
    });

    const handleOutsideClick = (event: MouseEvent) => {
        // Überprüfen, ob der Klick außerhalb des Dropdown-Bereichs erfolgt
        const userMenuButton = document.getElementById("user-menu-button");
        const isOutsideClick = userMenuButton && !userMenuButton.contains(event.target as Node);

        // Dropdown schließen, wenn der Klick außerhalb erfolgt
        if (isOutsideClick) {
            isMenuOpen = false;
        }
    };
</script>

<nav class="flex items-center bg-white py-4 shadow-md">
    <a href="/" class="text-blue-700 mx-4 hover:text-blue-500">Home</a>
    <a href="/news" class="text-blue-700 mx-4 hover:text-blue-500">News</a>
    <a href="/blogs" class="text-blue-700 mx-4 hover:text-blue-500">Blogs</a>
    {#if ($auth)}
        <div class="relative pr-5 ml-auto">
            <div>
                <button
                        type="button"
                        class="relative flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"
                        id="user-menu-button"
                        aria-expanded={isMenuOpen}
                        aria-haspopup="true"
                        on:click={toggleMenu}
                >
                    <span class="absolute -inset-1.5"></span>
                    <span class="sr-only">Open user menu</span>
                    <img
                            class="h-8 w-8 rounded-full"
                            src="https://media.istockphoto.com/id/1495088043/de/vektor/benutzerprofil-symbol-avatar-oder-personensymbol-profilbild-portr%C3%A4tsymbol-standard.jpg?s=612x612&w=0&k=20&c=mmj93kpr1sFn8VJYI_MUabWE4B86zRD5Uf9fBbTbQqk="
                            alt="Profile"
                    >
                </button>
            </div>


            {#if isMenuOpen}
                <div
                        class="absolute right-0 z-20 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
                        role="menu"
                        aria-orientation="vertical"
                        aria-labelledby="user-menu-button"
                        tabindex="-1"
                >
                    <!-- Active: "bg-gray-100", Not Active: "" -->
                    <a href="/yourProfile" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100" role="menuitem" tabindex="-1" id="user-menu-item-0">Your Profile</a>
                    <a href="/myBlogs" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100" role="menuitem" tabindex="-1" id="user-menu-item-2">My Blogs</a>
                    <a href="/settings" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100" role="menuitem" tabindex="-1" id="user-menu-item-1">Settings</a>
                    <a href="/signOut" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100" role="menuitem" tabindex="-1" id="user-menu-item-2">Sign out</a>
                </div>
            {/if}
        </div>
    {:else}
        <a href="/login" class="text-blue-700 mx-4 hover:text-blue-500 ml-auto">Sign In</a>
        /
        <a href="/register" class="text-blue-700 mx-4 hover:text-blue-500">Sign Up</a>
    {/if}
</nav>