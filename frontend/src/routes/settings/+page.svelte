<script lang="ts">
    import {auth} from "$lib/user";
    import img from '$lib/assets/EmployeePictogram.png';
    import SSO from "$lib/components/SSO.svelte";
    import {page} from "$app/stores";

    export let data;
</script>

<div class="flex flex-col gap-8 items-center justify-center h-screen pb-20">
    <form class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <div class="flex items-center mb-4">
            <img src={img} alt="User Icon" class="w-8 h-8 mr-4">

            <hr class="border-t border-gray-400 w-full border-solid h-0.5">
        </div>

        <div class="mb-4 pt-4">
            <p class="text-xl font-bold mb-2">Good evening {auth.username}</p>
            <hr>

            {#if data.value}
                <div class="flex items-center justify-center space-x-12 mt-3">
                    <button class="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-400" onclick="window.location.href='/FASetup';" disabled>
                        Activate 2FA
                    </button>
                    <span class="text-green-500">Active</span>
                    <button class="bg-red-500 text-white px-4 py-2 rounded disabled:bg-gray-400" onclick="window.location.href='/FASetup';">
                        Disable 2FA
                    </button>
                </div>

            {:else}
                <div class="flex items-center justify-center space-x-11 mt-3">
                    <button class="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-400" onclick="window.location.href='/FASetup';">
                        Activate 2FA
                    </button>
                    <span class="text-red-600">Disabled</span>
                    <button class="bg-red-500 text-white px-4 py-2 rounded disabled:bg-gray-400" onclick="window.location.href='/FASetup';" disabled>
                        Disable 2FA
                    </button>
                </div>
            {/if}
        </div>
    </form>
    <form class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <h2 class="font-bold text-xl">SSO</h2>
        <hr/>
        <div class="mt-3">
            <h3 class="font-semibold mb-1 text-lg">Active</h3>
            <p class="text-gray-500">None</p>
        </div>
        <div class="mt-3">
            <h3 class="font-semibold mb-1 text-lg">Add new</h3>
            <SSO type="continue" action="settings"/>
            {#if ($page.url.searchParams.has("sso_error"))}
                <span class="text-red-600">{$page.url.searchParams.get("sso_error")}</span>
            {:else if ($page.url.searchParams.has("sso_success"))}
                <span class="text-green-500">SSO Provider successfully added to your account.</span>
            {/if}
        </div>
    </form>
</div>
