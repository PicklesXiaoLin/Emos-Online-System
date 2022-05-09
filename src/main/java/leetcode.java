

    public class leetcode {
        public static void main(String[] args) {
            int[] nums = {-1,1,2,-4};
            int target = 1;
            int result = arrays(nums, target);
            System.out.println(result);
        }

        public static int arrays(int [] nums, int target){
            int closestSum = nums[0] + nums[1] + nums[2];
            for(int index=0;index<nums.length-2;index++){
                int left = index + 1, right = nums.length - 1;
                while(left<right){
                    int currentSum = nums[index] + nums[left] + nums[right];

                    if (Math.abs(currentSum-target) < Math.abs(closestSum)){
                        closestSum = currentSum;
                    }

                    if(currentSum == target){
                        return currentSum;
                    }else if(currentSum<target){
                        left = left + 1;
                    }else {
                        right = right - 1;
                    }
                }

            }
            return closestSum;
        }

    }

