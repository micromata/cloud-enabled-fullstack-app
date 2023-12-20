<script lang="ts">
    import {auth} from "$lib/user";
    import img from '$lib/assets/EmployeePictogram.png';
    import {getGreeting} from '$lib/getGreeting'
    import Modal from "$lib/components/Modal.svelte";
    import SSO from "$lib/components/SSO.svelte";
    import {page} from "$app/stores";
    import {ssoProviderMap} from "$lib/ssoProviderMap";
    import Modal from "$lib/components/Modal.svelte";

    let modalOpen: boolean = false;
    let isDeactivateConfirmed: boolean | null = null;

    function confirmDisable2FA() {
        modalOpen = !modalOpen;
    }

    let modalOpen: boolean = false;
    let isDeactivateConfirmed: boolean | null = null;

    function confirmDisable2FA() {
        modalOpen = !modalOpen;
    }

    export let data;
    export let form;

    $: hasSSOProviders = data.ssoProviders && data.ssoProviders.length !== 0;
</script>


<div class="flex flex-col gap-8 items-center justify-center h-screen pb-20">
    <div class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <div class="flex items-center mb-4">
            <img src={img} alt="User Icon" class="w-8 h-8 mr-4">

            <hr class="border-t border-gray-400 w-full border-solid h-0.5">
        </div>

        <div class="mb-4 pt-4">
            <p class="text-xl font-bold mb-2">{getGreeting()} {$auth.username}</p>
            <hr>

            {#if data.value}
                <div class="flex items-center justify-center space-x-12 mt-3">
                    <button class="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-400" onclick="window.location.href='/FASetup';" disabled>
                        Activate 2FA
                    </button>
                    <span class="text-green-500">Active</span>
                    <button class="bg-red-500 text-white px-4 py-2 rounded disabled:bg-gray-400" on:click={confirmDisable2FA}>
                        Disable 2FA
                    </button>
                </div>

            {:else}
                <div class="flex items-center justify-center space-x-11 mt-3">
                    <button class="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-400" onclick="window.location.href='/FASetup';">
                        Activate 2FA
                    </button>
                    <span class="text-red-600">Disabled</span>
                    <button class="bg-red-500 text-white px-4 py-2 rounded disabled:bg-gray-400" on:click={confirmDisable2FA} disabled>
                        Disable 2FA
                    </button>
                </div>
            {/if}


            <!-- Modal Confirm deactivate 2fa -->
            {#if modalOpen}
                <div class="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">
                    <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>

                    <div class="fixed inset-0 z-10 w-screen overflow-y-auto pb-80">
                        <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                            <div class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                                <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                                    <div class="sm:flex sm:items-start">
                                        <div class="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                            <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
                                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126zM12 15.75h.007v.008H12v-.008z" />
                                            </svg>
                                        </div>
                                        <div class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                            <h3 class="text-base font-semibold leading-6 text-gray-900" id="modal-title">Deactivate 2FA</h3>
                                            <div class="mt-2">
                                                <p class="text-sm text-gray-500">Are you sure you want to deactivate 2FA? You can reactivate it whenever you want!</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                    <form method="POST">
                                        <button type="submit" class="inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-500 sm:ml-3 sm:w-auto">Deactivate</button>
                                    </form>
                                    <button type="button" on:click={confirmDisable2FA} class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            {/if}
        </div>
    </div>
    <form class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <h2 class="font-bold text-2xl">SSO</h2>
        <hr/>
        <div class="mt-3">
            <h3 class="font-semibold mb-1 text-xl">Active</h3>
            {#if (hasSSOProviders)}
                <div class="grid gap-2 my-2">
                    {#each data.ssoProviders as ssoProvider}
                        <form action="?/deleteSSO" method="post" class="flex border-2 p-2 rounded-md justify-between items-center">
                            <input type="hidden" name="providerId" value={ssoProvider.id}>
                            <div class="flex flex-col">
                                <h4 class="font-bold">{ssoProviderMap[ssoProvider.provider]?.name}</h4>
                                <span class="text-sm text-gray-500">{ssoProvider.email}</span>
                            </div>
                            <div class="">
                                <button class="bg-red-500 w-7 text-white aspect-square p-1 rounded">X</button>
                            </div>
                        </form>
                    {/each}
                </div>
            {:else}
                <p class="text-gray-500">None</p>
            {/if}
            {#if (form?.error)}
                <span class="text-red-600">{form.error}</span>
            {:else if (form?.success)}
                <span class="text-green-500">SSO Provider deleted.</span>
            {/if}
        </div>
        <div class="mt-3">
            <h3 class="font-semibold mb-1 text-lg">Add new</h3>
            {#if ($page.url.searchParams.has("sso_error"))}
                <span class="text-red-600">{$page.url.searchParams.get("sso_error")}</span>
            {:else if ($page.url.searchParams.has("sso_success"))}
                <span class="text-green-500">SSO Provider successfully added to your account.</span>
            {/if}
            <SSO type="continue" action="settings"/>
        </div>
    </form>
</div>
