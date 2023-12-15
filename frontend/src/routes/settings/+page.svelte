<script lang="ts">
    import {auth} from "$lib/user";
    import img from '$lib/assets/EmployeePictogram.png';

    export let data;
</script>

<div class="flex items-center justify-center h-screen pb-80">
    <form class="w-full max-w-md bg-white shadow-md rounded-md p-6">
        <div class="flex items-center mb-4">
            <img src={img} alt="User Icon" class="w-8 h-8 mr-4">

            <hr class="border-t border-gray-400 w-full border-solid h-0.5">
        </div>

        <div class="mb-4 pt">
            <p class="text-xl font-bold mb-2">Good evening {$auth.username}</p>
            <hr>
            <button class="bg-blue-500 text-white px-4 py-2 rounded mt-3" onclick="window.location.href='/FASetup';">
                Activate 2FA
            </button>
        </div>
    </form>
</div>